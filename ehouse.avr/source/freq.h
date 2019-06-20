#include <inttypes.h>

#ifndef FREQ_HEADER

	#define FREQ_HEADER	

	#ifndef FREQ_BODY
	
		extern uint8_t freqDevice;

		extern void freqInit();
		extern void freqStart();
		extern uint8_t freqCheck();
		extern uint32_t freqGet();

	#endif

#endif
