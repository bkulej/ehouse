#define WDT_BODY

#include "devices.h"
#include "watchdog.h"
#include <inttypes.h>
#include <avr/interrupt.h>
#include <avr/wdt.h>

#ifdef WDT_MODULE

volatile uint8_t wdtCounter = 0; // Licznik watchdoga

void wdtDisable() {
	wdt_disable();
}

void wdtEnable() {
	cli();
	wdt_reset();
	WDTCSR = 0x00;
	WDTCSR = _BV(WDIF) | _BV(WDIE) | _BV(WDCE) | _BV(WDE) | _BV(WDP3) | _BV(WDP0);
	WDTCSR = _BV(WDIF) | _BV(WDIE) | _BV(WDP3) | _BV(WDP0);	
	MCUSR = 0x00;
	sei();
}


void wdtReset(void) {
	wdt_reset();
	wdtCounter = 0;
}


void wdtRestart(void) {
	wdt_enable(WDTO_500MS);
	while (1);
}


/* Obs³uga przerwania watchdoga
 */
ISR(WDT_vect) {
	if(wdtCounter == WDT_PERIOD) {
		wdtRestart();
	}	
	++wdtCounter;
}

#endif
