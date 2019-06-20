#define INPUT_BODY


#include "devices.h"
#include "debug.h"
#include "input.h"
#include "timer.h"
#include "pin.h"
#include "event.h"
#include "eeprom.h"
#include "mess.h"
#include "utils.h"
#include "address.h"
#include "decoder.h"
#include <inttypes.h>
#include <avr/io.h>
#include <avr/interrupt.h>


/* Konfiguracja urzadzenia
	0 DEVICE(1) urzadzenie przypisane do konfiguracji
	1 CON(2) - Konfiguracja, opóŸnienie w 100ms
	3 ADR1(2) – Address ACTION1 
	5 COM1(1) - Command ACTION1
	6 PAR1(2) – Params ACTION1
	8 ADR2(2) – Address ACTION1
	A COM2(1) – Command ACTION1
	B PAR2(2) – Params ACTION1
*/


#ifdef INPUT_MODULE

#define INPUT_DELAY				0x01
#define INPUT_ACTION1			0x03
#define INPUT_ACTION2			0x08

#define INPUT_TYPE_ACTIVE		0x01
#define INPUT_TYPE_PULL_UP		0x10

/* Konfiguracja output
			PARAM1	PARAM2
INPUT		IN		0x00		- aktywne 0, bez pullup
INPUT		IN		0x01		- aktywne 1, bez pullup
INPUT		IN		0x10		- aktywne 0, z pullup
INPUT		IN		0x11		- aktywne 1, z pullup

*/

#if MCU_XXX8	
void inputInit(void) {	
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type == DEV_TYPE_INPUT) {		
			DEBUG_SNN("INPUT INIT dev, pin:", i, devicesTab[i].param1);			
			uint8_t port = devicesTab[i].param1 & 0xF0;
			uint8_t pin = _BV(devicesTab[i].param1 & 0x0F);
			uint8_t pull = (devicesTab[i].param2 & INPUT_TYPE_PULL_UP) ? pin : 0;	
			switch(port) {			
				case 0xB0 : {										
					DDRB &= ~pin;					
					PORTB |= pull;					
					PCICR |= _BV(PCIE0);
					PCMSK0 |= pin;
				} break;
				case 0xC0 : {					
					DDRC &= ~pin;
					PORTC |= pull;					
					PCICR |= _BV(PCIE1);
					PCMSK1 |= pin;
				} break;
				case 0xD0 :	{					
					DDRD &= ~pin;					
					PORTD |= pull;					
					PCICR |= _BV(PCIE2);
					PCMSK2 |= pin;
				} break;					
			}				
		}				
	}		
}

#endif


ISR(PCINT0_vect) {		
	timerStart(TIMER_INPUT);
}


ISR(PCINT1_vect) {	
	timerStart(TIMER_INPUT);
}


ISR(PCINT2_vect) {		
	timerStart(TIMER_INPUT);
}


void inputEvent(void) {
	uint8_t active = 0;		
	// Obs³uga wejsc
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type  == DEV_TYPE_INPUT) {		
			// Pobierz dane
			uint16_t delay = eepromReadWord((devicesTab[i].config << 4) + INPUT_DELAY);
			uint8_t pinTmp = PIN_IS_SET1(i);
			uint8_t pinActive = (devicesTab[i].param2 & INPUT_TYPE_ACTIVE) ? pinTmp : !pinTmp;	
			// Czy jest podane opóŸnienie
			if(!delay) {
				// Czy przycisk zosta³ nacisniety
				if(pinActive && !devicesTab[i].timer) {
					messConfigSend(i,INPUT_ACTION1);
				}
				// Czy naciœniêty i nie przekroczony licznik
				if(pinActive && (devicesTab[i].timer < 0xFFFF)) {
					++devicesTab[i].timer;
				}
				// Czy przycisk zosta³ puszczony
				if(!pinActive && devicesTab[i].timer) {
					messConfigSend(i,INPUT_ACTION2);
					devicesTab[i].timer = 0;
				}
			} else {
				// Czy naciœniêty i nie przekroczony licznik
				if(pinActive && (devicesTab[i].timer < 0xFFFF)) {
					++devicesTab[i].timer;
				}		
				// Czy przycisk zostal puszczony 
				if(!pinActive && devicesTab[i].timer) {		
					// Czy czas jest mniejszy od zadanego opoznienia		
					if(devicesTab[i].timer <= delay) {
						messConfigSend(i,INPUT_ACTION1);				
					} else {
						messConfigSend(i,INPUT_ACTION2);				
					}								
					devicesTab[i].timer = 0;
				}		
			}
			// Czy potrzebny do timera
			if(pinActive) {
				active = 1;
			}										
		}
	}				
	if(!active) {		
		timerStop(TIMER_INPUT);
	}
}


#endif
