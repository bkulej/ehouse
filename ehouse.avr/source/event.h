#include <inttypes.h>

#ifndef EVENT_HEADER

	#define EVENT_HEADER
	
	#define EVENT_CHANNEL1 			0x0001		
	#define EVENT_CONN				0x0002
	#define EVENT_OUTPUT			0x0004	
	#define EVENT_INPUT				0x0008
	#define EVENT_THERM				0x0010
	#define EVENT_VOLT				0x0020

	#ifndef EVENT_BODY

		/* Obs³uga eventów
		*/
		void eventService();

		/* Dodanie wyspecyfikowanego eventu 
			param:
		    	event - podany event
		*/
		extern void eventAdd(uint32_t event);

		/* Sprawdzenie czy dany event wyst¹pi³ i wyczyszczenie eventu
			param:
		    	event - podany event
		   	return: 
		    	0000h   - brak eventu
			 	<>0000h - wyst¹pi³ event
		*/
		extern uint32_t eventCheck(uint32_t event);

	#endif

#endif
