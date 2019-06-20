#define PWM_BODY

#include "devices.h"
#include "debug.h"
#include "pin.h"
#include "pwm.h"
#include <avr/io.h>

#ifdef PWM_MODULE

void pwmInit() {
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type == DEV_TYPE_PWM) {
			DEBUG_SNN("PWM INIT dev,pin:",i,devicesTab[i].param1);
			// Pin jako output
			PIN_OUTPUT1(i);
			// TIMER 0-A
			if(devicesTab[i].param1 == PWM_PIN_OC0A) {
				// Prescaler
				TCCR0B |= devicesTab[i].param2 & 0x07;
				// Uaktywnienie kana³u A
				TCCR0A &= ~_BV(COM0A1);
				// Wyzerowanie kana³u
				OCR0A = 0;
			// TIMER 0-B
			} else if(devicesTab[i].param1 == PWM_PIN_OC0B) {
				// Prescaler
				TCCR0B |= devicesTab[i].param2 & 0x07;
				// Uaktywnienie kana³u B
				TCCR0A &= ~_BV(COM0B1);
				// Wyzerowanie kana³u
				OCR0B = 0; 
			}
		}
	}
	// Ustawienie trybu FAST PWM
	TCCR0A |= _BV(WGM01) | _BV(WGM00);
}


void pwmSet(uint8_t device, uint8_t value) {
	if(devicesTab[device].param1 == PWM_PIN_OC0A) {
		if(value != 0) {
			TCCR0A |= _BV(COM0A1);
			OCR0A = value;	
		} else {
			TCCR0A &= ~_BV(COM0A1);
		}
		
	} else if(devicesTab[device].param1 == PWM_PIN_OC0B) {
		if(value != 0) {
			TCCR0A |= _BV(COM0B1);
			OCR0B = value;
		} else {
			TCCR0A &= ~_BV(COM0B1);
		}
	}
	devicesTab[device].value = value;
}


uint8_t pwmGet(uint8_t device) {
	return devicesTab[device].value;
}

#endif
