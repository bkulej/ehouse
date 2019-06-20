#include <inttypes.h>

#ifndef CRC8_HEADER

	#define CRC8_HEADER
	
	#ifndef CRC8_BODY

		/* Obliczenie CRC
		*/ 
    	extern uint8_t crc8Update(uint8_t crc, uint8_t byte);

	#endif

#endif
