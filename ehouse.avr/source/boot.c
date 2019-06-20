#define BOOT_BODY

#include "devices.h"
#include "boot.h"
#include "debug.h"
#include "eeprom.h"
#include "crc8.h"
#include <avr/boot.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include <avr/wdt.h>
#include <util/delay.h>


#if SOFTWARE == 0x0000

#define CHANNEL_DDR_RW				DDRD				// Rejestr do konfiguracji pinu
#define CHANNEL_PORT_RW				PORTD				// Port pinu
#define CHANNEL_BIT_RW				PD4					// Numer pinu
#define CHANNEL_SPEED 				25 					// 38461b/s dla 16MHz
#define CHANNEL_TX_BUFFER_SIZE 		28					// Rozmiar bufora nadawczego
#define CHANNEL_RX_BUFFER_SIZE 		BOOT_PAGE_SIZE + 64	// Rozmiar bufora odbiorczego

uint8_t channelRxBuffer[CHANNEL_RX_BUFFER_SIZE];	
uint8_t channelTxBuffer[CHANNEL_TX_BUFFER_SIZE];

#define BITS4_TO_CHAR(value, channel1TxPos) ((value>>channel1TxPos)&0xf)>9 ? (uint8_t)(((value>>channel1TxPos)&0xf)+ 55) :(uint8_t)(((value>>channel1TxPos)&0xf)+ 48)
#define CHAR_TO_BITS4(value) (value < 'A' ? value - 48 : value - 55)

/* Zapisuje bajt do pamieci eeprom
 */
void bootStatusWrite(uint8_t ucData) {	
	// ustaw addres
	EEAR = EPPROM_HARDWARE_STATUS;
	// ustaw bajt do zapisu
	EEDR = ucData;
	// Zapisanie do eproma
	EECR |= _BV(EEMPE);
	EECR |= _BV(EEPE);
	// czekaj na zakoñczenie zapisywania
	while(EECR & _BV(EEPE) );
}


/* Zwraca bajt zapisany pod podanym addresem z pamieci eeprom
 */
uint8_t bootStatusRead() {
	// ustaw addres
	EEAR = EPPROM_HARDWARE_STATUS;
	// umo¿liw odczyt
	EECR |= _BV(EERE);
	// zwrócenie odczytanego bajtu
	return (EEDR == 0xFF ? BOOT_STATUS_EMPTY : EEDR);
}


// Metoda g³owna bootloadera
int main(void) {			
	// Wylaczenie przerwan
	cli();
	MCUSR = 0;	
	wdt_disable();	
	// Sprawdz status
	if (bootStatusRead() == BOOT_STATUS_NORMAL ) {
		// Przejdz do programu glownego
		boot_rww_enable();       
		(*((void(*)(void))0x0000))();				
	}			
	
	// ----- Inicjacja kana³u -----------------------------------------------------------------------------
	// Ustawienie prêdkosci
	UBRR0L = CHANNEL_SPEED;
	// Asynchroniczna, parzystosc rowna, 1 bity stop, 8 bitow
	UCSR0C = _BV(UPM01) | _BV(UCSZ01) | _BV(UCSZ00);	
	// Odblokowanie odbiornika i nadajnika oraz przerwañ odbiornika
	UCSR0B = _BV(RXEN0)|_BV(TXEN0);
	// Inicjacja pinu prze³aczania trybów
	CHANNEL_DDR_RW |= _BV(CHANNEL_BIT_RW);	
	// Ustaw pin w tryb nas³uchu
	CHANNEL_PORT_RW &= ~_BV(CHANNEL_BIT_RW);
	
	DEBUG_INIT;
	DEBUG_S("BOOT_START");
	
	// ----- Pêtla g³owna programu-----------------------------------------------------------------------		
	uint8_t request;
	uint8_t response;
	uint16_t addressReceived;	
	uint16_t addressSaved;							
	
	while(1){		
							
		// ----- Odczytanie wiadomosci -------------------------------------------------------------------		
		//uint8_t channelRxBuffer[CHANNEL_RX_BUFFER_SIZE];	
		uint8_t rxCrcCounted;	// crc obliczone
		uint8_t rxCrcReceived;  // crc odebrane
		uint16_t rxCount = 0;	// Ilosc bajtów w buforze
		uint8_t rxStarted = 0;	// By³ rozkaz startu
		uint16_t byteNumber = 0;	// licznik zlicza kolejne bajty po bajcie startu					
		while(1) {		
			// Oczekuj i pobierz dana
			while ( !(UCSR0A & (1<<RXC0)) );		
			uint8_t value = UDR0;			
			DEBUG_C(value);		
			// Czy dana wejdzie do bufora i czy nie ma bledu transmisji
			if(rxCount >= CHANNEL_RX_BUFFER_SIZE) {						
				//Wystapi³ blad
				rxStarted = 0;					
			} else if(value == 0x82) {					
				// Bajt startowy				
				rxStarted = 1;													
				rxCrcCounted = crc8Update(0,value); 										
				byteNumber = 0;
				rxCount = 0;					
			} else if(rxStarted && ((value & 0xF0)==0xA0)) {				
				// Bajt stopu 1			
				rxCrcReceived = value<<4;		
			} else if(rxStarted && ((value & 0xF0)==0xB0)) {
				// Bajt stopu 2
				rxStarted = 0;
				rxCrcReceived |= value & 0x0f;
				if (rxCrcCounted == rxCrcReceived) {
					break;
				}
			} else if(rxStarted) {			
				// Bajt danej
				++byteNumber;	
				rxCrcCounted = crc8Update(rxCrcCounted,value);							
				// Dekoduj wiadomosc			
				if (byteNumber <= 8) {
					if(value !=  BOOT_GET_SERIAL_PART(byteNumber - 1)) {
						// Niepoprawny serial					
						rxStarted = 0;			
						DEBUG_S("BAD SER");		
					}								
				} else {					
					//ID[0],REQUEST[1],ADDRESS[2-3],DATA[4..N]
					// Dane z komunikatu
					if(byteNumber & 0x01) {
						// Starsze 4 bity
						channelRxBuffer[rxCount] = CHAR_TO_BITS4(value) << 4;  				
					} else {
						// M³odsze 4 bity						
						channelRxBuffer[rxCount++] |= CHAR_TO_BITS4(value) & 0x0F;  						
					}					
				}						  			
			}				
		}		
		
		// ----- Dekodowanie rozkazu ---------------------------------------------------------------		
		request = channelRxBuffer[1];
		addressReceived = (channelRxBuffer[2]<<8) | channelRxBuffer[3]; 
		response = BOOT_OK_RESPONSE;	
		if(request == BOOT_FIND_REQUEST) {			
		} else if(request == BOOT_BEGIN_REQUEST) {			
			// Rozpoczecie procedury zapisu
			bootStatusWrite(BOOT_STATUS_EMPTY);										
			addressSaved = 0xFFFF;
		} else if(request == BOOT_SAVE_REQUEST) {			
			if(addressReceived != addressSaved) {								
				// Wyczyszczenie strony
				boot_page_erase (addressReceived);
				boot_spm_busy_wait ();
				if(rxCount>0) {
					// Wypelnienie strony
					for (uint16_t i=4; i < rxCount; i+=2) {            
						uint16_t w = channelRxBuffer[i] | (channelRxBuffer[i+1] << 8);						
						boot_page_fill(addressReceived+i-4, w);
					}
					// Zapisanie strony
					boot_page_write(addressReceived);
					boot_spm_busy_wait();
				}								
				addressSaved = addressReceived;
			}						
		} else if(request == BOOT_END_REQUEST) {					
			// Zakonczenie procedury zapisywania
			bootStatusWrite(BOOT_STATUS_BOOT);										
		} else if(request == BOOT_GO_REQUEST) {
			// Przejscie do programu wlasciwego			
			if(bootStatusRead() == BOOT_STATUS_BOOT) {
				// Zakonczenie procedury zapisywania
				bootStatusWrite(BOOT_STATUS_NORMAL);				
				(*((void(*)(void))0x0000))();			
			} else {
				response = BOOT_ERROR_RESPONSE;
			}			
		} else {			
			response = BOOT_ERROR_RESPONSE;
		}		
				
		// ----- Wyslanie powitania i potwierdzenia ----------------------------------------------------------------------------------------
		_delay_ms(10);
		// Dodanie nag³ówka
		channelTxBuffer[0] = 0x82;
		// Dodanie seriala 1 - 8		
		for(uint8_t i = 0; i < 8; ++i ) {			
			channelTxBuffer[i+1] = BOOT_GET_SERIAL_PART(i);		
		}
		// Dodanie id
		channelTxBuffer[9]  = BITS4_TO_CHAR(channelRxBuffer[0],4);
		channelTxBuffer[10] = BITS4_TO_CHAR(channelRxBuffer[0],0);
		// Dodanie rozkazu do bufora
		channelTxBuffer[11] = BITS4_TO_CHAR(response,4);
		channelTxBuffer[12] = BITS4_TO_CHAR(response,0);
		// Dodanie architektury
		channelTxBuffer[13] = BITS4_TO_CHAR(BOOT_HARDWARE,12);
		channelTxBuffer[14] = BITS4_TO_CHAR(BOOT_HARDWARE,8);
		channelTxBuffer[15] = BITS4_TO_CHAR(BOOT_HARDWARE,4);
		channelTxBuffer[16] = BITS4_TO_CHAR(BOOT_HARDWARE,0);
		// Dodanie rozmiaru pamieci
		channelTxBuffer[17] = BITS4_TO_CHAR(BOOT_PROGRAM_SIZE,12); 
		channelTxBuffer[18] = BITS4_TO_CHAR(BOOT_PROGRAM_SIZE,8); 
		channelTxBuffer[19] = BITS4_TO_CHAR(BOOT_PROGRAM_SIZE,4); 
		channelTxBuffer[20] = BITS4_TO_CHAR(BOOT_PROGRAM_SIZE,0);
		// Dodanie rozmairu strony
		channelTxBuffer[21] = BITS4_TO_CHAR(BOOT_PAGE_SIZE,12);
		channelTxBuffer[22] = BITS4_TO_CHAR(BOOT_PAGE_SIZE,8);
		channelTxBuffer[23] = BITS4_TO_CHAR(BOOT_PAGE_SIZE,4);
		channelTxBuffer[24] = BITS4_TO_CHAR(BOOT_PAGE_SIZE,0);
		// Dodanie statusu		
		channelTxBuffer[25] = bootStatusRead();
		// Obliczenie crc
		uint8_t channelTxCrc = 0;
		for(uint8_t i = 0; i < (CHANNEL_TX_BUFFER_SIZE-2); ++i) {
			channelTxCrc = crc8Update(channelTxCrc, channelTxBuffer[i]);	
		}
		// Dodanie stopu 1
		channelTxBuffer[26] = 0xA0 | (channelTxCrc>>4);
		// Dodanie stopu 2
		channelTxBuffer[27] = 0xB0 | (0x0F & channelTxCrc);
		// Prze³aczenie pinu na tryb transmisji
		CHANNEL_PORT_RW |= _BV(CHANNEL_BIT_RW);
		// Wyslij dane
		for(uint8_t i = 0; i < CHANNEL_TX_BUFFER_SIZE; ++i ) {								
			while (!(UCSR0A & (1<<UDRE0)));	
			UCSR0A |= _BV(TXC0);
			UDR0 = channelTxBuffer[i];						
		}	
		// Poczekaj na wyslanie reszty	
		while ( !( UCSR0A & (1<<TXC0)) );
		// Prze³aczenie pinu na tryb odbioru
		CHANNEL_PORT_RW &= ~_BV(CHANNEL_BIT_RW);								
	}		
}

#endif