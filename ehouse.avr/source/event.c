#define EVENT_BODY

#include "devices.h"
#include "debug.h"
#include "event.h"
#include "mess.h"
#include "conn.h"
#include <inttypes.h>

#ifdef EVENT_MODULE

// Przechowywane s¹ wyst¹pienia eventów
volatile uint16_t eventEvents = 0x0000;


/* Dodanie wyspecyfikowanego eventu 
*/
void eventAdd(uint32_t event){	
	eventEvents |= event;
}


/* Sprawdzenie czy dany event wyst¹pi³ i wyczyszczenie eventu
*/
uint32_t eventCheck(uint32_t event){
	// Wy³uskanie eventu
	uint32_t tmp = eventEvents & event;
	// Czy znaleziono event
	if(tmp) {		
		// Wyczyszczenie eventu
		eventEvents &= ~event;
	}
	return tmp;
}


#endif
