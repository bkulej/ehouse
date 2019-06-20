#include <inttypes.h>

#ifndef PIN_HEADER

	#define PIN_HEADER	
	
	#define PIN_OUTPUT	1
	#define PIN_INPUT	0	
	
	#define PIN_OUTPUT1(nsd) pinInit(devicesTab[nsd].param1,PIN_OUTPUT)
	#define PIN_OUTPUT2(nsd) pinInit(devicesTab[nsd].param2,PIN_OUTPUT)	
	#define PIN_INPUT1(nsd) pinInit(devicesTab[nsd].param1,PIN_INPUT)
	#define PIN_INPUT2(nsd) pinInit(devicesTab[nsd].param2,PIN_INPUT)
	#define PIN_SET1(nsd) pinSet(devicesTab[nsd].param1,1)
	#define PIN_SET2(nsd) pinSet(devicesTab[nsd].param2,1)	
	#define PIN_RESET1(nsd) pinSet(devicesTab[nsd].param1,0)
	#define PIN_RESET2(nsd) pinSet(devicesTab[nsd].param2,0)
	#define PIN_IS_SET1(nsd) pinGet(devicesTab[nsd].param1)
	#define PIN_IS_SET2(nsd) pinGet(devicesTab[nsd].param2)

	#ifndef PIN_BODY		

		/* Ustawienie urz퉐zenia jako output 
			param:
		    	device - numer urz퉐zenia
		*/
		extern void pinInit(uint8_t portpin, uint8_t output);


		/* Ustawienie pinu  
			param:
		    	device - numer urz퉐zenia
		*/
		extern void pinSet(uint8_t portpin, uint8_t state);


		/* Sprawdznie pinu 
			param:
		    	device - numer urz퉐zenia
		*/
		extern uint8_t pinGet(uint8_t portpin);
		

	#endif

#endif
