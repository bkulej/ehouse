#define THERM_BODY

#include "devices.h"
#include "debug.h"
#include "therm.h"
#include "owire.h"
#include "utils.h"
#include "event.h"
#include "pin.h"
#include "timer.h"
#include "crc8.h"
#include <inttypes.h>
#include <util/delay.h>

#ifdef THERM_MODULE

void thermEvent(void);

void thermInit() {
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type == DEV_TYPE_THERM) {		
			owireInit(i);
		}
	}			
	thermEvent();
	timerStart(TIMER_THERM);
	_delay_ms(1000);
}


/* Obs³uga eventu
 */
void thermEvent(void) {
	for(uint8_t i = 0; i < DEV_COUNT; ++i) {
		if((devicesTab[i].type == DEV_TYPE_THERM) && (owireReset(i)==OWIRE_OK)) {
			owireWrite(i,0xCC);
			owireWrite(i,0x44);			
		}
	}	
}

/** Odczyt temperatury urz¹dzenia		
*/
int16_t thermRead(uint8_t device){		
	
	for(uint8_t i=0; i < 10; ++i) {
		if(owireReset(device)==OWIRE_ERR){
			return THERM_ERR;
		}
		owireWrite(device,0xCC);
		owireWrite(device,0xBE);
		uint8_t tl = owireRead(device);
		uint8_t crcCalc = crc8Update(0, tl);
		uint8_t th = owireRead(device);
		crcCalc = crc8Update(crcCalc, th);
		crcCalc = crc8Update(crcCalc, owireRead(device));
		crcCalc = crc8Update(crcCalc, owireRead(device));
		crcCalc = crc8Update(crcCalc, owireRead(device));
		crcCalc = crc8Update(crcCalc, owireRead(device));
		crcCalc = crc8Update(crcCalc, owireRead(device));
		crcCalc = crc8Update(crcCalc, owireRead(device));
		if(crcCalc == owireRead(device)) {
			int16_t temp = (th << 8) | tl;
			return temp * 10 / 16;
		}
		_delay_ms(2);
	}	
	return THERM_BAD;
}


#endif
