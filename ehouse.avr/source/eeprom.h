#include <inttypes.h>

#ifndef EEPROM_HEADER

	#define EEPROM_HEADER	
	
	#define EPPROM_EMPTY_CELL					0xFF
	
	#define EPPROM_HARDWARE_STATUS				0x0000
	#define EPPROM_HIGH_ADDRESS					0x0001
	#define EPPROM_LOW_ADDRESS					0x0002
	#define EPPROM_DEVICES_CONFIG				0x0010
	

	#ifndef EEPROM_BODY

		/* zapisuje bajt do pamieci eeprom
		 */
		extern void eepromWriteByte(uint16_t address, uint8_t data);

		/* zwraca bajt zapisany pod podanym addresem z pamieci eeprom
 		*/		
		extern uint8_t eepromReadByte(uint16_t address);	
		
		/* zwraca s³owo zapisany pod podanym addresem z pamieci eeprom
 		*/		
		extern uint16_t eepromReadWord(uint16_t address);					

	#endif

#endif