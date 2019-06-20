#define CRC8_BODY

#include "devices.h"
#include "crc8.h"
#include <inttypes.h>

#ifdef CRC8_MODULE

/* Obliczenie CRC8
 */
/*uint8_t crc8Update(uint8_t crc, uint8_t value) { 
	crc ^= value; 
   	uint8_t i; 
   	for(i=0; i<8; i++) 
    	crc = (crc << 1) ^ ((crc & 0x80)?0x07:0); 
   	return crc; 
}*/

uint8_t crc8Update(uint8_t crc, uint8_t data) {
	crc = crc ^ data;
	for(uint8_t i = 0; i < 8; i++){
		if (crc & 0x01) {
			crc = (crc >> 1) ^ 0x8C;
			} else {
			crc >>= 1;
		}
	}
	return crc;
}

#endif
