#define UTILS_BODY

#include "devices.h"
#include "debug.h"
#include "utils.h"
#include "mess.h"
#include "eeprom.h"
#include "boot.h"
#include <stdlib.h>
#include <avr/io.h>
#include <util/delay.h>

#ifdef UTILS_MODULE

/* Konwersja znaku na 4 wybrane bity
*/
uint16_t utilsCharToBits(uint8_t chr, uint8_t bits) {
	uint16_t result;
	if(chr <= '9') {
	    result = chr - '0';
	} else if(chr <= 'F') {
	    result = chr - 55;
	} else {
	    result = chr - 87;
	}
	return result << bits;
}


/* Konwersja 4 wybranych bitów do znaku
*/
uint8_t utilsBitsToChar(uint16_t data, uint8_t bits) {
    uint8_t value = 0x0F & (data >> bits);
    if(value < 10) {
        value += '0';
    } else {
        value += 55;
    }
    return value;
}

#endif




