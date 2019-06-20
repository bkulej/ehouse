#define POWER_BODY

#include "devices.h"
#include "debug.h"
#include "power.h"
#include <avr/sleep.h>

#ifdef POWER_MODULE

/* inicjuje modu� power
 */
void powerInit() {
	// w��czenie trybu spania leniwego
	set_sleep_mode(SLEEP_MODE_IDLE);
}

/* wy��cza dzia�anie CPU i FLASH dopuki nie nadejdzie nast�puj�ce zdarzenie :
	- u�ycie TIMERx gdzie x jest 1, 2 lub 3
	- u�ycie SPI
	- u�ycie UART
	- u�ycie ADC
	- u�ycie TWI
	- u�ycie WatchDoga
	- the interrupt system to continue operating
	�r�d�o: 38 strona dokumentacji, sam d�
 */
void powerIdle() {
	// w��czenie trybu spania
	sleep_mode();	
}

#endif
