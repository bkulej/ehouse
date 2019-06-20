#define DEBUG_BODY

/* 
Modu� DEBUG 115200 kb/s, 2 bity stopu, brak parzysto�ci
*/

#include "devices.h"
#include "debug.h"
#include <inttypes.h>
#include <avr/io.h>
#include <util/delay.h>
#include <util/atomic.h>

#if DEBUG_MODULE == 1

#if MCU_XXX8
#define DEBUG_DDR		DDRB			// Rejestr do konfiguracji 
#define DEBUG_PORT		PORTB			// Port 
#define DEBUG_BIT		PB3				// Pin
#define DEBUG_DELAY		8				// 115200kb/s = 8
#endif

#if MCU_XXX4
#define DEBUG_DDR		DDRB			// Rejestr do konfiguracji pinu zegara
#define DEBUG_PORT		PORTB			// Port
#define DEBUG_BIT		PB5				// Pin
#define DEBUG_DELAY		8				// 115200kb/s = 8,
#endif


/* Inicjacja debugera
*/
void debugPinsInit() {
	DEBUG_DDR |= _BV(DEBUG_BIT);
	DEBUG_PORT |= _BV(DEBUG_BIT);	
}


/* Wys�anie bajtu
*/
void debugPinsChar(uint8_t data) {
	ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
		// Bit startu
		DEBUG_PORT &= ~_BV(DEBUG_BIT);
		_delay_us(DEBUG_DELAY);
		// Bity danych
		for(uint8_t i=0; i<8; ++i) {
			if(data & 0x01) {
				DEBUG_PORT |= _BV(DEBUG_BIT);	
			} else {
				DEBUG_PORT &= ~_BV(DEBUG_BIT);
			}
			data >>=1;
			_delay_us(DEBUG_DELAY);
		}
		// Bit stopu
		DEBUG_PORT = _BV(DEBUG_BIT);	
		_delay_us(DEBUG_DELAY*2);
	}	
}


/* Wys�anie komunikatu
*/
void debugPinsSendSN(char *message,uint8_t arg, uint32_t value) {
	// Wys�anie komunitatu
	for(int i=0; message[i]!=0; i++){
		debugPinsChar(message[i]);
	}
	//Wys�anie liczby
	if(arg>0) {
		for(int i=28;i>=0;i=i-4) {
			uint8_t chr = ((value>>i)&0xf)>9 
							?(uint8_t)(((value>>i)&0xf)+ 55)
							:(uint8_t)(((value>>i)&0xf)+ 48);
			debugPinsChar(chr);
			if((i<32) && (i>0) && ((i%arg)==0)) {
				debugPinsChar(',');
			}
		}	
	}
	//Wys�anie ko�ca lini
	debugPinsChar('\n');	
}


/* Wys�anie komunikatu
*/
void debugPinsSendN(uint8_t value) {	
	//Wys�anie liczby
	uint8_t tmp = (value >> 4) & 0x0F;
	debugPinsChar(tmp>9 ? tmp+55 : tmp+48);			
	tmp = value & 0x0F;
	debugPinsChar(tmp>9 ? tmp+55 : tmp+48);
	//Wys�anie ko�ca lini
	debugPinsChar('\n');	
}


/* Wys�anie komunikatu
*/
void debugPinsSendSS(char *message,char *message1) {
	// Wys�anie komunitatu
	for(int i=0; message[i]!=0; i++){
		debugPinsChar(message[i]);
	}	
	// Wys�anie wiadomosci
	for(int i=0; message1[i]!=0; i++){
		debugPinsChar(message1[i]);
	}		
	//Wys�anie ko�ca lini
	debugPinsChar('\n');	
}


/* Wys�anie komunikatu
*/
void debugPinsSendSC(char *message,char data) {
	// Wys�anie komunitatu
	for(int i=0; message[i]!=0; i++){
		debugPinsChar(message[i]);
	}
	// Wys�ane znaku
	debugPinsChar(data);
	//Wys�anie ko�ca lini
	debugPinsChar('\n');	
}


#endif
