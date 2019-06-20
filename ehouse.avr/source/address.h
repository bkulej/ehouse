#include <inttypes.h>

#ifndef ADDRESS_HEADER

	#define ADDRESS_HEADER	

	#define ADDRESS_BROADCAST			0x0000
	#define ADDRESS_EMPTY				0xFFFF
	#define ADDRESS_COMPUTER			0x0001

	#ifndef ADDRESS_BODY

	
		/* Pobiera adres urz¹dzenia
		*/
		extern uint16_t addressGet(void);		


		/* Sprawdzenie adresu
		 */
		extern uint8_t addressCheck(uint8_t type, uint16_t add);
		
		
		/* Sprawdzeni czy adress jest broadcastem
		*/
		extern uint8_t addressIsBroadcast(uint16_t add);


  	#endif

#endif
