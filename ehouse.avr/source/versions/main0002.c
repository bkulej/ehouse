#include "../devices.h"
#include "../debug.h"
#include "../utils.h"
#include "../lcdt.h"
#include "../therm.h"
#include "../pin.h"
#include "../gsm.h"
#include <inttypes.h>
#include <avr/io.h>

// ATMEGA644
#if SOFTWARE == 0x0002

volatile uint16_t main_wait = 0;

void mainInit() {	
}

void mainThread() {			
	//utilsDelayMs(100);
	//gsmModemText("ATD609561216;\r");		
}

uint8_t mainRequestReceive(int8_t channel, uint8_t ndd) {
	return DECODER_RESULT_OK;	
}

void mainResponseReceive(int8_t channel) {		
}


void mainResponseSend(uint8_t id, uint16_t add, uint8_t nsd,  uint16_t requestCommand, int8_t requestResult) {		
}

#endif