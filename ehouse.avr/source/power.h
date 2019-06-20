#include <inttypes.h>

#ifndef POWER_HEADER

	#define POWER_HEADER

	#ifndef POWER_BODY

		/* inicjuje modu³ POWER
		*/
		extern void powerInit();

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
		extern void powerIdle();

  	#endif

#endif
