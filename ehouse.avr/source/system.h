#include <inttypes.h>

#ifndef SYSTEM_HEADER

	#define SYSTEM_HEADER
	
	#define SYSTEM_DEVICE_NUMBER		0
	#define SYSTEM_EXECUTE_NO			0
	#define SYSTEM_EXECUTE_YES			1		
	
	#ifndef SYSTEM_BODY

		extern uint8_t systemExecuteMakro(uint16_t makro);
		extern void systemRequestReceive(uint8_t com, uint8_t param1, uint8_t param2);
		extern void systemResponseSend(uint8_t id, uint16_t add, uint8_t nsd,  uint8_t com, uint8_t param1, uint8_t param2);
		
  	#endif

#endif