#include <inttypes.h>

#ifndef WDT_HEADER

	#define WDT_HEADER
	
	#define WDT_PERIOD	250		// Maksymalny czas restaru w okresach 8sekundowych, ponad 30  minut

	#ifndef WDT_BODY

		extern void wdtDisable();

		extern void wdtEnable();
		
		extern void wdtReset();
		
		extern void wdtRestart();

  	#endif

#endif
