#define POWER_BODY

#include "devices.h"
#include "debug.h"
#include "power.h"
#include <avr/sleep.h>

#ifdef POWER_MODULE

/* inicjuje modu³ power
 */
void powerInit() {
	// w³¹czenie trybu spania leniwego
	set_sleep_mode(SLEEP_MODE_IDLE);
}

/* wy³¹cza dzia³anie CPU i FLASH dopuki nie nadejdzie nastêpujêce zdarzenie :
	- u¿ycie TIMERx gdzie x jest 1, 2 lub 3
	- u¿ycie SPI
	- u¿ycie UART
	- u¿ycie ADC
	- u¿ycie TWI
	- u¿ycie WatchDoga
	- the interrupt system to continue operating
	Ÿród³o: 38 strona dokumentacji, sam dó³
 */
void powerIdle() {
	// w³¹czenie trybu spania
	sleep_mode();	
}

#endif
