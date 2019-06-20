#include <inttypes.h>

#ifndef POWER_HEADER

	#define POWER_HEADER

	#ifndef POWER_BODY

		/* inicjuje modu� POWER
		*/
		extern void powerInit();

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
		extern void powerIdle();

  	#endif

#endif
