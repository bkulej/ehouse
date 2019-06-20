#include <inttypes.h>

#ifndef THERM_HEADER

	#define THERM_HEADER
	
	#define THERM_OK	0			// Poprawna inicjajca
	#define THERM_NI	10000		// Termometr nie zainicjowany
	#define THERM_ERR	10001		// B³ad oczytu timera
	#define THERM_BAD	10002		// Niepoerawny pomiar
	

	#ifndef THERM_BODY

		/* Inicjacja termometrow */
		extern void thermInit();

		/* Obs³uga eventu
 		*/
		extern void thermEvent(void);

		/** Odczyt temperatury urz¹dzenia
				return:
					THERM_OK  - start rozpoczety
					THERM_ERR - b³ad odczytu					
		*/
		extern uint16_t thermStart(uint8_t device);

		/** Odczyt temperatury urz¹dzenia
				return:
					THERM_ERR - b³ad odczytu
					wartosc - temperatura pomno¿ona przez 10 np. 105 = 10,5
		*/
		extern int16_t thermRead(uint8_t device);		

		/* Obs³uga przerwania timera
 		*/
		extern void thermTimer(void);

  	#endif

#endif
