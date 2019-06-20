#include <inttypes.h>

#ifndef TIMER_HEADER

	#define TIMER_HEADER

	#define TIMER_CONN 		0x01
	#define TIMER_THERM		0x02
	#define TIMER_INPUT		0x04
	#define TIMER_OUTPUT	0x08
	#define TIMER_VOLT		0x10
	#define TIMER_MAIN		0x20

	#ifndef TIMER_BODY
	
		/* Inicjacja timera */
		extern void timerInit(void);

		/* Uruchomienie timera */
		extern void timerStart(uint8_t source);

		/* Zatrzymanie timera */
		extern void timerStop(uint8_t source);
		
		/* Ustawienie zadanego czasu oczekiwania */
		extern void timerSetWait(uint16_t second);
		
		/* Oczekiwanie zadany czas */
		extern uint8_t timerCheckWait();

	#endif

#endif
