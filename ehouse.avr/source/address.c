#define ADDRESS_BODY

#include "devices.h"
#include "debug.h"
#include "address.h"
#include "mess.h"
#include "eeprom.h"
#include "boot.h"
#include "decoder.h"
#include "utils.h"
#include "channel1.h"
#include <avr/pgmspace.h>
#include <stdlib.h>
#include <avr/io.h>

#ifdef ADDRESS_MODULE

/* Pobiera adres urz¹dzenia
*/
uint16_t addressGet(void) {
	// Sprawdzenia czy aby adres nie pusty
	#if DEBUG_ADDRESS == 1
		return 0x0110;
	#else
		return eepromReadWord(EPPROM_HIGH_ADDRESS);
	#endif
}


uint8_t addressIsBroadcast(uint16_t add) {
	if(add == (addressGet() & 0xFF00)) {
		return 1;
	}
	if(add == ADDRESS_BROADCAST) {
		return 1;
	}
	return 0;
}


/* Sprawdzenie adresacji urz¹dzenia
*/
uint8_t addressCheck(uint8_t type, uint16_t add) {
	if(type == MESS_TYPE_ADDRESS) {
		if((add & 0xFFF0) == addressGet()) {
			return 1;
		}
		return addressIsBroadcast(add);
	} else if(type == MESS_TYPE_SERIAL) {
		// Sprawdzenie broadcast seriala
		int8_t count0 = 0;
		for(uint8_t i = 0; i < 4; ++i) {
			if(MESS_GET_SERIAL(i) == 0) {
				++count0;
				} else {
				break;
			}
		}
		if(count0 == 4) {
			return 1;
		}
		// Sprawdzenie konkretnego seriala
		for(uint8_t i = 0; i < 8; i=i+2) {
			uint8_t data = MESS_GET_SERIAL((i>>1));
			uint8_t part = utilsCharToBits(BOOT_GET_SERIAL_PART(i),4) | utilsCharToBits(BOOT_GET_SERIAL_PART(i+1),0);
			if(part != data) {
				return 0;
			}
		}
		return 1;
	} else {
		return 0;
	}
}

#endif

