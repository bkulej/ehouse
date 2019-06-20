#define CHANNEL1_BODY

#include "devices.h"
#include "debug.h"
#include "channel1.h"
#include "crc8.h"
#include "event.h"
#include "timer.h"
#include "utils.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#ifdef CHANNEL1_MODULE

/* Definicje pinów w urz¹dzeniu */
#define CHANNEL1_DDR_RW			DDRD			// Rejestr do konfiguracji pinu
#define CHANNEL1_PORT_RW		PORTD			// Port pinu
#define CHANNEL1_BIT_RW			PD4				// Numer pinu

#define CHANNEL1_SPEED_UBRR 			25 	// 38461b/s dla 16MHz

#define CHANNEL1_RX_BUFFER_SIZE 		26	// Rozmiar bufora odbiorczego

volatile uint8_t channel1TxCrc;
volatile uint8_t channel1RxBuffer[CHANNEL1_RX_BUFFER_SIZE];
volatile uint8_t channel1RxCount;
volatile uint8_t channel1RxBusy;
volatile uint8_t channel1RxCopy;
volatile uint8_t channel1PartLow;


/* Inicjacja modu³u transmisji
 */
void channel1Init(void) {	
	// Ustawienie szybkosci	
	UBRR0H = (uint8_t)(CHANNEL1_SPEED_UBRR>>8);	
	UBRR0L = (uint8_t)CHANNEL1_SPEED_UBRR;
	// Asynchroniczna, parzystosc rowna, 1 bity stop, 8 bitow
	UCSR0C = _BV(UPM01) | _BV(UCSZ01) | _BV(UCSZ00);	
	// Odblokowanie odbiornika i nadajnika oraz przerwañ odbiornika
	UCSR0B = _BV(RXEN0)|_BV(TXEN0) | _BV(RXCIE0);
	// Inicjacja pinu prze³aczania trybów
	CHANNEL1_DDR_RW |= _BV(CHANNEL1_BIT_RW);	
	// Ustaw pin w tryb nas³uchu
	CHANNEL1_PORT_RW &= ~_BV(CHANNEL1_BIT_RW);	
}


/* Rozpoczecie wysylanie
*/
void channel1Send(uint8_t data) {
	while ( !( UCSR0A & (1<<UDRE0)) );
	UCSR0A |= _BV(TXC0);
	UDR0 = data;	
}

/* Rozpoczêcie nowego komunikatu
 */
void channel1New(uint8_t header, uint8_t rxCopy) {
	// Poczekaj na zakonczenie odbioru
	while(channel1RxBusy);
	// Zablokuj odbiornik
	UCSR0B &= ~_BV(RXEN0);
	// Prze³aczenie pinu na tryb transmisji
	CHANNEL1_PORT_RW |= _BV(CHANNEL1_BIT_RW);
	_delay_ms(10);
	// Wyslanie naglowka
	channel1Send(0x80 | header);		
	// Wyczyszczenie crc i dodanie pierwszego znaku
	channel1TxCrc = crc8Update(0, 0x80 | header);	
	// Kopiowanie do bufora odbiorczego
	channel1RxCopy = rxCopy;
	if(channel1RxCopy) {
		channel1RxCount = 0;
		channel1RxBuffer[channel1RxCount++] = 0x80 | header;
	}
}


/* Wstawienie 8 bajtów do komunikatu
 */
void channel1Put(uint8_t data) {	
	// Starsze 4 bity
	uint8_t chr = utilsBitsToChar(data,4);
	channel1Send(chr);
	channel1TxCrc = crc8Update(channel1TxCrc, chr);
	// Mlodsze 4 bity
	chr = utilsBitsToChar(data,0);
	channel1Send(chr);
	channel1TxCrc = crc8Update(channel1TxCrc, chr);
	// Skopiowanie do bufora odbiorczego
	if(channel1RxCopy && (channel1RxCount < CHANNEL1_RX_BUFFER_SIZE)) {
		// Dodaj dan¹ do bufora
		channel1RxBuffer[channel1RxCount++] = data;
	}
}


/* Wys³anie komunikatu
 */
void channel1End() {
	// Po wyslaniu czysc bufor automatycznie
	channel1Send(0xA0 | channel1TxCrc>>4);
	channel1Send(0xB0 | (0x0f & channel1TxCrc));
	if(channel1RxCopy) {
		// Dodaj event
		eventAdd(EVENT_CHANNEL1);
	}
	// Poczekaj na wyslanie reszty
	while ( !( UCSR0A & (1<<TXC0)) );
	// Prze³aczenie pinu na tryb odbioru
	CHANNEL1_PORT_RW &= ~_BV(CHANNEL1_BIT_RW);
	// Odblokuj odbiornik
	UCSR0B |= _BV(RXEN0);
}


/* Pobranie danej z bufora
 */
uint8_t channel1Get(uint8_t i) {
	return (i < CHANNEL1_RX_BUFFER_SIZE) ? channel1RxBuffer[i] : 0;
}


/* Wyczyszczenie bufora
 */
void channel1RxClear(void) {
	// Wyzeruj licznik
	channel1RxCount=0;
	// Odblokuj odbiornik
	UCSR0B |= _BV(RXEN0); 
}


/* Obs³uga przerwania odebrania znaku
 */
#if MCU_XXX4
ISR(USART0_RX_vect) {
#endif

#if MCU_0088
ISR(USART_RX_vect) {
#endif

#if MCU_0168 || MCU_0328
ISR(USART_RX_vect) {	
#endif
    volatile static uint8_t crc_c; // CRC obliczane
	volatile static uint8_t crc_r; // CRC odebrane
	volatile static uint8_t tmp;   // Tymczasowy licznik

	// Pobranie danej z rejestru
	uint8_t value = UDR0;
	//DEBUG_C(value);

	// Czy jest cos w buforze
	if(!channel1RxCount) {
		
		// Czy bajt startu
		if((value & 0xF0) == 0x80) {
			// Wyzerowanie CRC
			crc_c=0;
			// Odbiornik zajêty
			channel1RxBusy=1;
			// Wyzerowanie tymczasowego licznika
			tmp = 0;
			// Aktualizacja CRC8
			crc_c = crc8Update(crc_c,value);
			// Dodanie do bufora
			channel1RxBuffer[tmp++] = value;
			// Ustwaienie typu bajtu
			channel1PartLow = 0;
			
		} else if(channel1RxBusy) {

			// Czy bajt stopu 1
			if((value & 0xF0) == 0xA0) {
				// Pobranie CRC
				crc_r = value<<4;
			} else if((value & 0xF0) == 0xB0) {
				// Bajt stopu 2
				// Pobranie CRC
				crc_r |=  value & 0x0F;
				// Sprawdzenie CRC
				if(crc_r==crc_c) {
					// CRC poprawne
					channel1RxCount = tmp;
					// Zablokuj odbiornik
					UCSR0B &= ~_BV(RXEN0);
					// Dodaj event
					eventAdd(EVENT_CHANNEL1);
				}
				// Odbiornik wolny
				channel1RxBusy=0;
				
			} else if(tmp < CHANNEL1_RX_BUFFER_SIZE) {
				
				// Aktualizacja CRC8
				crc_c = crc8Update(crc_c,value);
				// Wstawienie do bufora
				if (!channel1PartLow) {
					// Bity starsze
					channel1RxBuffer[tmp] = utilsCharToBits(value,4);
					channel1PartLow = 1;
				} else {
					// Bity m³odsze
					channel1RxBuffer[tmp++] |= utilsCharToBits(value,0);
					channel1PartLow = 0;
				}
			}
		}
		
		// Czy wystapi³ b³¹d transmisji bajtu
		if ( UCSR0A & ( _BV(FE0) | _BV(DOR0) | _BV(UPE0) ) ) {
			// Blad transmisji
			channel1RxBusy=0;
		}
	}
}


#endif



