#define OUTPUT_BODY

#include "devices.h"
#include "debug.h"
#include "output.h"
#include "pin.h"
#include "timer.h"
#include "utils.h"
#include "mess.h"
#include "decoder.h"
#include <inttypes.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#ifdef OUTPUT_MODULE

#define OUTPUT_SWITCH_TIME		50		// czas prze³aczenia w ms


/*
Konfiguracja output

			HARD				PARAM1	PARAM2	
1 x MONO	DEV_HARD_OUTPUT1	ON		0x00	
------------------------------------------------
2 x MONO	DEV_HARD_OUTPUT1	ON		0x00	
			DEV_HARD_OUTPUT2	ON		0x00	
------------------------------------------------
1 x BIST	DEV_HARD_OUTPUT1	ON		OFF		
------------------------------------------------
2 x BIST	DEV_HARD_OUTPUT1	ON		OFF		
			DEV_HARD_OUTPUT2	ON		OFF		
*/


/** Inicjacja magistrali
*/
void outputInit(void) {
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {		
		if(devicesTab[i].type == DEV_TYPE_OUTPUT1 || devicesTab[i].type == DEV_TYPE_OUTPUT2) {
			DEBUG_SNNNN("OUTPUT INIT dev, type, pin1, pin2:", i, devicesTab[i].type, devicesTab[i].param1, devicesTab[i].param2);
			if(devicesTab[i].param1) {
				PIN_OUTPUT1(i);
				PIN_RESET1(i);
			}			
			if(devicesTab[i].param2) {
				PIN_OUTPUT2(i);
				PIN_RESET2(i);
			}			
		}
		devicesTab[i].value = OUTPUT_OFF;
	}		
}


void outputSetDevice(uint8_t device, uint8_t hard, uint8_t state, uint16_t period) {	
	// Sprawdz device
	if(devicesTab[device].type != hard) {
		return;
	}	
	// Wykonaj operacje
	uint8_t bist = devicesTab[device].param2;
	if(!state && !bist) {		
		// 0 i MONO	
		PIN_RESET1(device);
	} else if(state && !bist) {		
		// 1 i MONO
		PIN_SET1(device);
	} else if(!state && bist) { 			
		// 0 i BIST
		PIN_SET2(device);
		_delay_ms(OUTPUT_SWITCH_TIME);
		PIN_RESET2(device);
	} else {		
		// 1 i BIST
		PIN_SET1(device);
		_delay_ms(OUTPUT_SWITCH_TIME);
		PIN_RESET1(device);
	}
	// Ustawienie stanu
	devicesTab[device].value = state;
	// Obliczenie czasu trwania - (4095 sekundy max)
	devicesTab[device].timer = (period & 0x0FFF) * 10;
	// Uruchamianie timera
	if(period) { 			
		timerStart(TIMER_OUTPUT);
	}	
}


/* Pobranie informacji o urz¹dzeniu
*/
uint8_t outputGet(uint8_t device) {
	if(devicesTab[device].type == DEV_TYPE_OUTPUT1 && devicesTab[device].value) {
		return OUTPUT_OPEN;
	}
	if(devicesTab[device+1].type == DEV_TYPE_OUTPUT2 && devicesTab[device+1].value) {
		return OUTPUT_CLOSE;
	}
	return OUTPUT_OFF;
}


void outputSet(uint8_t ndd, uint8_t state, uint16_t period) {
	uint8_t status =  devicesTab[ndd].value |  devicesTab[ndd+1].value;
	switch(state) {
		case OUTPUT_OFF: {
			outputSetDevice(ndd,DEV_TYPE_OUTPUT1,OUTPUT_OFF,period);
			outputSetDevice(ndd+1,DEV_TYPE_OUTPUT2,OUTPUT_OFF,0);
			break;
		}
		case OUTPUT_OPEN: {
			outputSetDevice(ndd+1,DEV_TYPE_OUTPUT2,OUTPUT_OFF,0);
			outputSetDevice(ndd,DEV_TYPE_OUTPUT1,OUTPUT_OPEN,period);
			break;
		}
		case OUTPUT_OPEN_OR_OFF: {
			outputSetDevice(ndd+1,DEV_TYPE_OUTPUT2,OUTPUT_OFF,0);			
			outputSetDevice(ndd,DEV_TYPE_OUTPUT1, status?OUTPUT_OFF:OUTPUT_OPEN, period);
			break;
		}
		case OUTPUT_CLOSE: {
			outputSetDevice(ndd,DEV_TYPE_OUTPUT1,OUTPUT_OFF,0);
			outputSetDevice(ndd+1,DEV_TYPE_OUTPUT2,OUTPUT_OPEN,period);
			break;
		}
		case OUTPUT_CLOSE_OR_OFF: {
			outputSetDevice(ndd,DEV_TYPE_OUTPUT1,OUTPUT_OFF,0);
			outputSetDevice(ndd+1,DEV_TYPE_OUTPUT2,status?OUTPUT_OFF:OUTPUT_OPEN,period);
			break;
		}
		break;
	}
}


void outputEvent(void) {	
	uint8_t active = 0;	
	for(uint8_t i = 1; i <= DEV_COUNT; ++i) {	
		if(devicesTab[i].timer && (devicesTab[i].type == DEV_TYPE_OUTPUT1 || devicesTab[i].type == DEV_TYPE_OUTPUT2)) {							
			active = 1;
			--devicesTab[i].timer;
			if(!devicesTab[i].timer) {
				if(devicesTab[i].value) {
					outputSetDevice(i,devicesTab[i].type,OUTPUT_OFF,0);
				} else {
					outputSetDevice(i,devicesTab[i].type,OUTPUT_OPEN,0);
				}
			}
		}						
	}
	if(!active) {		
		timerStop(TIMER_OUTPUT);	
	}
}


#endif
