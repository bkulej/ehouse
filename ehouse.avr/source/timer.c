#define TIMER_BODY

#include "devices.h"
#include "debug.h"
#include "timer.h"
#include "channel1.h"
#include "conn.h"
#include "event.h"
#include "therm.h"
#include "input.h"
#include "output.h"
#include "volt.h"
#include "mess.h"
#include "freq.h"
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#ifdef TIMER_MODULE

/* Czêstotliwoœc generowania przerwañ to :
	zegar procesora / (prescaler * 256)
	czyli przy zegarze 16Mhz i prescalerze 1024 to
	16 000 000 /(1024*256) = 61,035 Hz czyli co 16,3 ms
*/

volatile uint8_t timerSource = 0x00;
volatile uint8_t timerTmp;
volatile uint32_t timerCount;


/* Ustawienie i uruchomienie timera
*/
void timerInit(void) {
	// Tryb normalny
  	TCCR2A = 0x00;	
	/* Enable Timer Overflow Interrupts */
	TIMSK2 |= _BV(TOIE2);	
}


/* Ustawienie i uruchomienie timera
*/
void timerStart(uint8_t source) {	
	// Czy dzia³a ju¿ jakieœ Ÿrod³o timera
	if(!timerSource) {
		// Wyzerowanie licznika 
		TCNT2 = 0x00;
		// Prescaler 1024
  		TCCR2B = _BV(CS20) | _BV(CS21) | _BV(CS22);
	}
	// Ustawienie Ÿród³a timera
	timerSource |= source;
}


/* Zatrzymanie timera 
*/
void timerStop(uint8_t source) {		
	// Wyczyszczenie Ÿród³a timera
	timerSource &= ~ source;
	// Czy dzia³a jeszcze jakieœ Ÿrod³o timera
	if(!timerSource) {
		// Timer stop
		TCCR2B = 0x00;
	}
}

/* Ustawienie czasu oczekiwania */
void timerSetWait(uint16_t second) {
	timerCount = second;
	timerCount *= 61;
	if(second) {
		timerStart(TIMER_MAIN);
	} else {
		timerStop(TIMER_MAIN);
	}
}

/* Oczekiwanie zadany czas */
uint8_t timerCheckWait() {
	return timerCount ? 1 : 0;
}


/* Obs³uga przerwania Timera 
*/
ISR(TIMER2_OVF_vect) {	
	
	++timerTmp;	
	// Obs³uga timera
	if(timerSource & TIMER_MAIN) {
		-- timerCount;
		if(!timerCount) {
			timerStop(TIMER_MAIN);
		}
	}

#ifdef THERM_MODULE
	// Obs³uga temperatury ok 2s
	if((timerSource & TIMER_THERM) && !(timerTmp % 128)) {
		eventAdd(EVENT_THERM);		
	}
#endif

#ifdef VOLT_MODULE
	// Obs³uga temperatury ok 1s
	if((timerSource & TIMER_VOLT) && !(timerTmp % 62)) {
		ADCSRA |= _BV(ADSC);
	}
#endif

#ifdef CONN_MODULE
	// Obs³uga komunikatów ok 200ms
	if((timerSource & TIMER_CONN) && !(timerTmp % 13)) {
		eventAdd(EVENT_CONN);
	}
#endif

#ifdef OUTPUT_MODULE
	// Obs³uga wlacznikow ok 100ms
	if((timerSource & TIMER_OUTPUT) && !(timerTmp % 6)) {
		eventAdd(EVENT_OUTPUT);
	}
#endif

#ifdef INPUT_MODULE
	// Obs³uga wejscia ok 50ms
	if((timerSource & TIMER_INPUT) && !(timerTmp % 3)) {
		eventAdd(EVENT_INPUT);
	}
#endif

}

#endif
