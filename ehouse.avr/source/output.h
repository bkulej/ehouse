#include <inttypes.h>

#ifndef OUTPUT_HEADER

	#define OUTPUT_HEADER
	
	#define OUTPUT_OFF				0x00
	#define OUTPUT_OPEN				0x01
	#define OUTPUT_CLOSE			0x02
	#define OUTPUT_OPEN_OR_OFF		0x03
	#define OUTPUT_CLOSE_OR_OFF		0x04		

	#ifndef OUTPUT_BODY		
	
		/** Inicjacja przekaznikow
		*/
		extern void outputInit(void);
		
		extern void outputSet(uint8_t device, uint8_t state, uint16_t period);		
						
		extern uint8_t outputGet(uint8_t device);
		
		extern void outputEvent(void);
		
  	#endif

#endif
