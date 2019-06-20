#include <inttypes.h>


#ifndef UTILS_HEADER

	#define UTILS_HEADER

	#ifndef UTILS_BODY			
		
		/* Konwersja znaku na 4 wybrane bity
		*/
		extern uint16_t utilsCharToBits(uint8_t chr, uint8_t bits);
		
		
		/* Konwersja 4 wybranych bitów do znaku
		*/
		extern uint8_t utilsBitsToChar(uint16_t number, uint8_t bits);
		
		
	#endif

#endif
