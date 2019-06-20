#define FREQ_BODY

#include "devices.h"
#include "debug.h"
#include "timer.h"
#include "pin.h"
#include "freq.h"
#include "mess.h"
#include <avr/io.h>
#include <avr/interrupt.h>

#ifdef FREQ_MODULE

volatile uint8_t freqStatus;
volatile uint16_t freqOver;
volatile uint16_t freqHigh;
volatile uint16_t freqFirst;
volatile uint16_t freqLast;

void freqInit() {
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type == DEV_TYPE_FREQ) {
			// Pin jako input
			PIN_INPUT1(i);
		}
	}
	// Tryb normalny
	TCCR1A = 0;
	// Prescaler 1:1 16MHz, mozna dodac Noise Canceler
	TCCR1B |= _BV(CS10) | _BV(ICNC1);
}

void freqStart() {
	// Pierwsze zbocze
	freqStatus = 0;
	freqFirst = 0;
	freqLast = 0;
	freqOver = 0;
	freqHigh = 0;
	// Przerwanie capture
	TIFR1 |= _BV(ICF1);
	TIMSK1 |= _BV(ICIE1);
}

uint32_t freqGet() {
	uint32_t counter = 65536 * freqHigh;
	counter += freqLast - freqFirst;
	return counter ? F_CPU / counter : 0;
}

ISR(TIMER1_CAPT_vect){  
	if(!freqStatus) {
		// Wyzerowanie danych
		freqFirst = ICR1;
		// Wyzeruj przepelnienie
		freqOver = 0;
		// Przerwanie przepelnienia
		TIFR1 |= _BV(TOV1);
		TIMSK1 |=  _BV(TOIE1);
	} else {
		// Pobranie danych koncowych
		freqLast = ICR1;
		freqHigh = freqOver;
		// Wylacz oba przerwania
		TIMSK1 &= ~( _BV(ICIE1) | _BV(TOIE1) );
	}
	++ freqStatus;
}

ISR(TIMER1_OVF_vect){
	++freqOver;  
}


#endif
