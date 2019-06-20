#include "../devices.h"
#include "../debug.h"
#include "../crc8.h"
#include "../decoder.h"
#include <avr/boot.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include <avr/wdt.h>
#include <util/delay.h>

/********************************************************************************************
Generator rozkazów wachdog

DECODER_REQUEST_COMMAND_11	Ustawienie temperatury

********************************************************************************************/
#if SOFTWARE == 0x0001

#define CHANNEL_DDR_RW				DDRD				// Rejestr do konfiguracji pinu
#define CHANNEL_PORT_RW				PORTD				// Port pinu
#define CHANNEL_BIT_RW				PD4					// Numer pinu
#define CHANNEL_SPEED 				25 					// 38461b/s dla 16MHz
#define CHANNEL_TX_BUFFER_SIZE 		15					// Rozmiar bufora nadawczego

#define WACHDOG_START				2000				// Opoznienie startu
#define WACHDOG_COUNT				5					// Iloœæ powtórzeñ rozkazów
#define WACHDOG_DELAY				500					// Powtórzenia co okreœlony czas
#define WACHDOG_WAIT				200000			    // Wysy³anie co 2 * 5 minut

#define WACHDOG_ADD					0x0000				// Adres docelowy
#define WACHDOG_ADS					0x0002				// Adress zrodlowy

#define MESS_MAX_ID					0xFF

uint8_t messId	= 0;
uint8_t channelTxBuffer[CHANNEL_TX_BUFFER_SIZE];

#define BITS4_TO_CHAR(value, channel1TxPos) ((value>>channel1TxPos)&0xf)>9 ? (uint8_t)(((value>>channel1TxPos)&0xf)+ 55) :(uint8_t)(((value>>channel1TxPos)&0xf)+ 48)

void initSystem(void) {
	// Wylaczenie przerwan
	cli();
	MCUSR = 0;
	wdt_disable();
}

void initChannel(void) {
	// Ustawienie prêdkosci
	UBRR0L = CHANNEL_SPEED;
	// Asynchroniczna, parzystosc rowna, 1 bity stop, 8 bitow
	UCSR0C = _BV(UPM01) | _BV(UCSZ01) | _BV(UCSZ00);
	// Odblokowanie odbiornika i nadajnika oraz przerwañ odbiornika
	UCSR0B = _BV(TXEN0);
	// Inicjacja pinu prze³aczania trybów
	CHANNEL_DDR_RW |= _BV(CHANNEL_BIT_RW);
	// Ustaw pin w tryb nas³uchu
	CHANNEL_PORT_RW &= ~_BV(CHANNEL_BIT_RW);
}

void initDebug(void) {
	DEBUG_INIT;
	DEBUG_S("MAIN_START");
}

/*	Generowanie ID
*/
uint8_t messGetId(void) {
	if(messId >= MESS_MAX_ID) {
		messId = 0;
	}
	++messId;
	return messId;
}

void prepareCommand(void) {
	// Dodanie nag³ówka
	channelTxBuffer[0] = 0x81;
	// Dodanie ADD
	channelTxBuffer[1] = BITS4_TO_CHAR(WACHDOG_ADD,12);
	channelTxBuffer[2] = BITS4_TO_CHAR(WACHDOG_ADD,8);
	channelTxBuffer[3] = BITS4_TO_CHAR(WACHDOG_ADD,4);
	channelTxBuffer[4] = BITS4_TO_CHAR(WACHDOG_ADD,0);
	// Dodanie ADS
	channelTxBuffer[5] = BITS4_TO_CHAR(WACHDOG_ADS,12);
	channelTxBuffer[6] = BITS4_TO_CHAR(WACHDOG_ADS,8);
	channelTxBuffer[7] = BITS4_TO_CHAR(WACHDOG_ADS,4);
	channelTxBuffer[8] = BITS4_TO_CHAR(WACHDOG_ADS,0);
	// Dodanie id
	uint8_t messageId = messGetId();
	channelTxBuffer[9]  = BITS4_TO_CHAR(messageId,4);
	channelTxBuffer[10] = BITS4_TO_CHAR(messageId,0);
	// Dodanie rozkazu do bufora
	uint8_t command = DECODER_WATCHDOG_RESET;
	channelTxBuffer[11] = BITS4_TO_CHAR(command,4);
	channelTxBuffer[12] = BITS4_TO_CHAR(command,0);
	// Obliczenie crc
	uint8_t channelTxCrc = 0;
	for(uint8_t i = 0; i < (CHANNEL_TX_BUFFER_SIZE-2); ++i) {
		channelTxCrc = crc8Update(channelTxCrc, channelTxBuffer[i]);
	}
	// Dodanie stopu 1
	channelTxBuffer[13] = 0xA0 | (channelTxCrc>>4);
	// Dodanie stopu 2
	channelTxBuffer[14] = 0xB0 | (0x0F & channelTxCrc);
}

void sendCommand() {	
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

void sendCommands() {
	prepareCommand();
	for(int i=0; i<WACHDOG_COUNT; ++i) {
		sendCommand();
		_delay_ms(WACHDOG_DELAY);
	}
}

int main(void) {
	initSystem();
	initChannel();
	initDebug();
	prepareCommand();
	_delay_ms(WACHDOG_START);
	while(1){
		sendCommands();
		_delay_ms(WACHDOG_WAIT);
		_delay_ms(WACHDOG_WAIT);
		_delay_ms(WACHDOG_WAIT);
	}
}

#endif
