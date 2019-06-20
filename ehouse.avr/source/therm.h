#include <inttypes.h>

#ifndef THERM_HEADER

	#define THERM_HEADER
	
	#define THERM_OK	0			// Poprawna inicjajca
	#define THERM_NI	10000		// Termometr nie zainicjowany
	#define THERM_ERR	10001		// B�ad oczytu timera
	#define THERM_BAD	10002		// Niepoerawny pomiar
	

	#ifndef THERM_BODY

		/* Inicjacja termometrow */
		extern void thermInit();

		/* Obs�uga eventu
 		*/
		extern void thermEvent(void);

		/** Odczyt temperatury urz�dzenia
				return:
					THERM_OK  - start rozpoczety
					THERM_ERR - b�ad odczytu					
		*/
		extern uint16_t thermStart(uint8_t device);

		/** Odczyt temperatury urz�dzenia
				return:
					THERM_ERR - b�ad odczytu
					wartosc - temperatura pomno�ona przez 10 np. 105 = 10,5
		*/
		extern int16_t thermRead(uint8_t device);		

		/* Obs�uga przerwania timera
 		*/
		extern void thermTimer(void);

  	#endif

#endif
