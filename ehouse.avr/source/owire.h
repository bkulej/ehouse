#include <inttypes.h>

#ifndef OWIRE_HEADER

	#define OWIRE_HEADER

	#define OWIRE_OK 	0
	#define OWIRE_ERR	1

	#ifndef OWIRE_BODY		
	
		/** Inicjacja magistrali
		*/
		extern void owireInit(uint8_t device);

		/** Reset magistrali
		*/
		extern uint8_t owireReset(uint8_t device);
		
		/** Wys³anie bajtu
		*/
		extern void owireWrite (uint8_t device, uint8_t data);
		
		/** Odczytanie bajtu 
		*/
		extern uint8_t owireRead (uint8_t device);		

  	#endif

#endif
