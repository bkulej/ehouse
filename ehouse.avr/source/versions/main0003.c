#include "../devices.h"
#include "../debug.h"
#include "../utils.h"
#include "../therm.h"
#include "../pin.h"
#include "../decoder.h"
#include "../output.h"
#include "../eeprom.h"
#include "../mess.h"
#include "../channel1.h"
#include "../timer.h"
#include "../main.h"
#include <inttypes.h>

/********************************************************************************************
Sterownik zaworu 3 drogowego

DECODER_REQUEST_COMMAND_11	Ustawienie temperatury

********************************************************************************************/

// ATMEGA88
#if SOFTWARE == 0x0003 && defined (DEVICE_MAIN)

#define MAIN_DEV_TEMP_VALVE_OUT		2	// Numer termometru wyjœciowego
#define MAIN_DEV_TEMP_VALVE_RET		3	// Numer termometru powrotu
#define MAIN_DEV_PUMP				4	// Numer urz¹dzenia pompy
#define MAIN_DEV_VALVE				5	// Numer urz¹dzenia zaworu

#define MAIN_TEMP_MAX				440 // Temperatura ogrzewania n/10 stopni
#define MAIN_TEMP_DEVIATION			10	// Odchylka o n/10 stopni która powoduje reakcje czujnika

volatile int16_t mainHeatTempSet;
volatile int16_t mainValveTempOut;
volatile int16_t mainValveTempRet;

void mainInit() {
	outputSet(MAIN_DEV_VALVE, OUTPUT_OFF,0);
	outputSet(MAIN_DEV_PUMP, OUTPUT_OFF, 0);
}

void mainThread() {
	
	// Oczekiwanie
	if(timerCheckWait()) return;
	timerSetWait(1);
	
	mainValveTempOut = thermRead(MAIN_DEV_TEMP_VALVE_OUT);
	mainContinue();
	mainValveTempRet = thermRead(MAIN_DEV_TEMP_VALVE_RET);
	mainContinue();
	
	if((mainHeatTempSet <= 0) || (mainHeatTempSet > MAIN_TEMP_MAX) || (mainValveTempOut > MAIN_TEMP_MAX)) {
		// Wy³¹czenie 
		if(outputGet(MAIN_DEV_PUMP) != OUTPUT_OFF) {
			outputSet(MAIN_DEV_PUMP, OUTPUT_OFF, 0);
		}
		if(outputGet(MAIN_DEV_VALVE) != OUTPUT_CLOSE) {
			outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		}	
		return;
	}
	
	// W³¹czenie pompy
	if(outputGet(MAIN_DEV_PUMP) == OUTPUT_OFF) {
		outputSet(MAIN_DEV_PUMP, OUTPUT_OPEN, 0);
	}
	
	// Wylaczenie zmiany temperatury
	if(outputGet(MAIN_DEV_VALVE) != OUTPUT_OFF) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_OFF, 0);
		timerSetWait(30);
		return;
	}
	
	// Czy temperatura za mala
	if(mainValveTempOut <= (mainHeatTempSet - MAIN_TEMP_DEVIATION)) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_OPEN, 0);
		int16_t diff = mainHeatTempSet - mainValveTempOut;
		if(diff > 50) {
			timerSetWait(5);	
		} else {
			timerSetWait(1);
		}	
		return;
	}
	
	// Czy temperatura za duza
	if(mainValveTempOut >= (mainHeatTempSet + MAIN_TEMP_DEVIATION)) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		int16_t diff = mainValveTempOut - mainHeatTempSet;
		if(diff > 50) {
			timerSetWait(10);
		} else {
			timerSetWait(1);
		}
		return;
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if((devicesTab[ndd].type == DEV_TYPE_HEAT_3WAY) && (com == DECODER_REQUEST_COMMAND_11)) {
		mainHeatTempSet =  param1 << 8 | param2;
	}
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {	
	if(devicesTab[nsd].type == DEV_TYPE_HEAT_3WAY) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		messPutWord(mainHeatTempSet);
		messPutWord(mainValveTempOut);
		messPutWord(mainValveTempRet);
		MESS_PUT_BYTE(outputGet(MAIN_DEV_PUMP));
		MESS_PUT_BYTE(outputGet(MAIN_DEV_VALVE));
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
}


#endif
