#define PIN_BODY

#include "devices.h"
#include "debug.h"
#include "pin.h"
#include <inttypes.h>
#include <avr/io.h>


#ifdef PIN_MODULE


void pinInit(uint8_t portpin, uint8_t output) {
	uint8_t port = portpin & 0xF0;
	uint8_t pin = _BV(portpin & 0x0F);
	switch(port) {	
#if MCU_XXX4			
		case 0xA0 : {					
			DDRA = output ? (DDRA | pin) : (DDRA & ~pin);
		} break;
#endif
		case 0xB0 : {					
			DDRB = output ? (DDRB | pin) : (DDRB & ~pin);
		} break;
		case 0xC0 : {			
			DDRC = output ? (DDRC | pin) : (DDRC & ~pin);
		} break;
		case 0xD0 :	{			
			DDRD = output ? (DDRD | pin) : (DDRD & ~pin);
		} break;	
	}
}


void pinSet(uint8_t portpin, uint8_t state) {
	uint8_t port = portpin & 0xF0;
	uint8_t pin = _BV(portpin & 0x0F);			
	switch(port) {	
#if MCU_XXX4			
		case 0xA0 : {
			PORTA = state ? (PORTA | pin) : (PORTA & ~pin);
		} break;
#endif
		case 0xB0 : {
			PORTB = state ? (PORTB | pin) : (PORTB & ~pin);
		} break;
		case 0xC0 : {
			PORTC = state ? (PORTC | pin) : (PORTC & ~pin);
		} break;
		case 0xD0 :	{
			PORTD = state ? (PORTD | pin) : (PORTD & ~pin);
		} break;	
	}
}


uint8_t pinGet(uint8_t portpin) {	
	uint8_t port = portpin & 0xF0;
	uint8_t pin = _BV(portpin & 0x0F);		
	switch(port) {
#if MCU_XXX4				
		case 0xA0 : return (PINA & pin) >0;
#endif
		case 0xB0 : return (PINB & pin) >0;
		case 0xC0 : return (PINC & pin) >0;
		case 0xD0 :	return (PIND & pin) >0;
	}
	return 0;
}


#endif
