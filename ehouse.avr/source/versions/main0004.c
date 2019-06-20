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
Sterownik zaworu 4 drogowego



********************************************************************************************/

// ATMEGA88
#if SOFTWARE == 0x0004 && defined (DEVICE_MAIN)

#define MAIN_DEV_MAIN				1	// Numer sterownika zaworu
#define MAIN_DEV_TEMP_BOILER_INP	2	// Numer termometru wejœciowego
#define MAIN_DEV_TEMP_VALVE_OUT		3	// Numer termometru wyjœciowego
#define MAIN_DEV_TEMP_VALVE_RET		4	// Numer termometru powrotu
#define MAIN_DEV_TEMP_BOILER_RET	5	// Numer termometru wejœciowego
#define MAIN_DEV_BOILER				6	// Numer urz¹dzenia pompy
#define MAIN_DEV_VALVE				7	// Numer urz¹dzenia zaworu

#define MAIN_TEMP_MIN				  0 // Temperatura maksymalna n/10 stopni
#define MAIN_TEMP_MAX				800 // Temperatura maksymalna pieca
#define MAIN_TEMP_MIN_BOLIER_INP	450 // Minimalna temperatura wejsciowa pieca		
#define MAIN_TEMP_MIN_BOLIER_RET	300 // Minimalna temperatura powrotu pieca

#define MAIN_TEMP_DEVIATION			10	// Odchylka o n/10 stopni która powoduje reakcje czujnika

volatile int16_t mainHeatTempSet;
volatile int16_t mainBoilerTempInp;
volatile int16_t mainValveTempOut;
volatile int16_t mainValveTempRet;
volatile int16_t mainBoilerTempRet;

void mainInit() {
	outputSet(MAIN_DEV_VALVE, OUTPUT_OFF,0);
	outputSet(MAIN_DEV_BOILER, OUTPUT_OFF, 0);
}


void mainThread() {
	
	// Oczekiwanie
	if(timerCheckWait()) return;
	timerSetWait(1);
	
	mainBoilerTempInp = thermRead(MAIN_DEV_TEMP_BOILER_INP);
	mainContinue();
	mainValveTempOut = thermRead(MAIN_DEV_TEMP_VALVE_OUT);
	mainContinue();
	mainValveTempRet = thermRead(MAIN_DEV_TEMP_VALVE_RET);
	mainContinue();
	mainBoilerTempRet = thermRead(MAIN_DEV_TEMP_BOILER_RET);
	mainContinue();
	
	if(mainBoilerTempInp > MAIN_TEMP_MAX && mainBoilerTempInp < THERM_NI) {
		messSystemSend(DECODER_SYSTEM_ALARM, MAIN_DEV_MAIN, DECODER_ALARM_BOLIER_TEMP_HIGH);
		timerSetWait(30);
	}
	
	if(mainHeatTempSet == MAIN_TEMP_MIN) {
		if(outputGet(MAIN_DEV_VALVE) != OUTPUT_CLOSE) {
			outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		}
		return;
	}
	
	if(mainHeatTempSet == MAIN_TEMP_MAX) {
		// Wy³¹czenie
		if(outputGet(MAIN_DEV_BOILER) != OUTPUT_OPEN) {
			outputSet(MAIN_DEV_BOILER, OUTPUT_OPEN, 0);
		}
		if(outputGet(MAIN_DEV_VALVE) != OUTPUT_OPEN) {
			outputSet(MAIN_DEV_VALVE, OUTPUT_OPEN, 0);
		}
		return;
	}
	
	if( (mainHeatTempSet < 0) || (mainHeatTempSet == THERM_ERR) || (mainBoilerTempInp  == THERM_ERR) 
		|| (mainValveTempOut  == THERM_ERR) || (mainBoilerTempRet  == THERM_ERR) ) {
		// Wy³¹czenie
		if(outputGet(MAIN_DEV_BOILER) != OUTPUT_OFF) {
			outputSet(MAIN_DEV_BOILER, OUTPUT_OFF, 0);
		}
		if(outputGet(MAIN_DEV_VALVE) != OUTPUT_CLOSE) {
			outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		}
		return;
	}
	
	// W³¹czenie pieca
	if(outputGet(MAIN_DEV_BOILER) == OUTPUT_OFF) {
		outputSet(MAIN_DEV_BOILER, OUTPUT_OPEN, 0);
	}
	
	// Wylaczenie zmiany temperatury
	if(outputGet(MAIN_DEV_VALVE) != OUTPUT_OFF) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_OFF, 0);
		timerSetWait(30);
		return;
	}
	
	// Czy wejscie pieca osi¹gnê³o minimaln¹ teperatur
	if(mainBoilerTempInp < MAIN_TEMP_MIN_BOLIER_INP) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		timerSetWait(30);
		return;
	}
	
	// Czy powrót pieca osi¹gn¹ minimaln¹ temperature
	if(mainBoilerTempRet < MAIN_TEMP_MIN_BOLIER_RET) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		timerSetWait(30);
		return;
	}
	
	// Czy temperatura za mala
	if(mainValveTempOut <= (mainHeatTempSet - MAIN_TEMP_DEVIATION)) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_OPEN, 0);
		if((mainHeatTempSet - mainValveTempOut) > 50) {
			timerSetWait(5);
		} else {
			timerSetWait(1);
		}
		return;
	}
	
	// Czy temperatura za duza
	if(mainValveTempOut >= (mainHeatTempSet + MAIN_TEMP_DEVIATION)) {
		outputSet(MAIN_DEV_VALVE, OUTPUT_CLOSE, 0);
		if((mainValveTempOut - mainHeatTempSet) > 50) {
			timerSetWait(5);
		} else {
			timerSetWait(2);
		}
		return;
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if((devicesTab[ndd].type == DEV_TYPE_HEAT_4WAY) && (com == DECODER_REQUEST_COMMAND_11)) {
			mainHeatTempSet =  param1 << 8 | param2;
	}
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {
	if(devicesTab[nsd].type == DEV_TYPE_HEAT_4WAY) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		messPutWord(mainHeatTempSet);
		messPutWord(mainBoilerTempInp);
		messPutWord(mainValveTempOut);
		messPutWord(mainValveTempRet);
		messPutWord(mainBoilerTempRet);
		MESS_PUT_BYTE(outputGet(MAIN_DEV_BOILER));
		MESS_PUT_BYTE(outputGet(MAIN_DEV_VALVE));
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
}

#endif
