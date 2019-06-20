#include "../devices.h"
#include "../debug.h"
#include "../utils.h"
#include "../mess.h"
#include "../decoder.h"
#include "../channel1.h"
#include "../timer.h"
#include "../eeprom.h"
#include "../pin.h"
#include "../output.h"
#include "../pwm.h"
#include "../freq.h"
#include "../main.h"
#include "../therm.h"
#include <inttypes.h>

/********************************************************************************************
Sterownik rekuperatora
Obs³ugiwane komendy:
10 - get status
11 - set mode
12 - set bypass
13 - set gwc

********************************************************************************************/

#if SOFTWARE == 0x0007 && defined (DEVICE_MAIN)

#define MAIN_DEV_MAIN			1	// Sterownik
#define MAIN_DEV_BYPASS			2	// Bypass
#define MAIN_DEV_OUTSIDE		3	// Czerpnia
#define MAIN_DEV_GWC			4	// GWC
#define MAIN_DEV_POWER			5	// Zasilanie
#define MAIN_DEV_FAN_IN			6	// Wentylator nawiewny
#define MAIN_DEV_FAN_OUT		7	// Wentylator wywiewny
#define MAIN_DEV_FAN_SPEED		8	// Predkosc wentylatora
#define MAIN_DEV_FAN_SELECT		9	// Wybór wentylatora do pomiaru predkosci
#define MAIN_DEV_TERM_REC_IN	10	// Temperatura z powietrza z zewnatrz
#define MAIN_DEV_TERM_ROOM_IN	11	// Temperatura powietrza na pokoje
#define MAIN_DEV_TERM_ROOM_OUT	12	// Temperatura pwietrza pobieranego z pokoj
#define MAIN_DEV_TERM_REC_OUT	13	// Temperatura powietrza wysy³anego na zewn¹trz
#define MAIN_DEV_TERM_GWC		14	// Temperatura powierza z GWC

volatile uint16_t mainConf;
volatile uint8_t mainModeNew;
volatile uint8_t mainModeCur;
volatile uint8_t mainBypass;
volatile uint8_t mainGwc;
volatile uint16_t mainSpeedFanIn;
volatile uint16_t mainSpeedFanOut;
volatile int16_t mainTempRecIn;
volatile int16_t mainTempRoomIn;
volatile int16_t mainTempRoomOut;
volatile int16_t mainTempRecOut;
volatile int16_t mainTempGwc;


void mainInit() {
	mainModeNew = 0;
	mainModeCur = 0;
	mainBypass = 0;
	mainGwc = 0;
	outputSet(MAIN_DEV_BYPASS,OUTPUT_OFF,0);
	outputSet(MAIN_DEV_GWC,OUTPUT_OFF,0);
	outputSet(MAIN_DEV_OUTSIDE,OUTPUT_OPEN,0);
	outputSet(MAIN_DEV_POWER,OUTPUT_OFF,0);
	outputSet(MAIN_DEV_FAN_SELECT,OUTPUT_OFF,0);
	pwmSet(MAIN_DEV_FAN_IN,0);
	pwmSet(MAIN_DEV_FAN_OUT,0);
}


void mainThread() {
		
	timerSetWait(1);
	if(timerCheckWait()) mainContinue();
	
	// Sprawdzenie konfiga
	uint16_t address = devicesTab[MAIN_DEV_MAIN].config << 4;
	if(eepromReadByte(address) != MAIN_DEV_MAIN) {
		return;
	}
	
	// Pomiar wentylarto IN
	outputSet(MAIN_DEV_FAN_SELECT,OUTPUT_OFF,0);
	freqStart();
	timerSetWait(2);
	while(timerCheckWait()) mainContinue();
	mainSpeedFanIn = 60 * freqGet();
	
	// Pomiar wentylartora OUT
	outputSet(MAIN_DEV_FAN_SELECT,OUTPUT_OPEN,0);
	freqStart();
	timerSetWait(2);
	while(timerCheckWait()) mainContinue();
	mainSpeedFanOut = 60 * freqGet();
	
	// Pomiar temperatur
	mainTempRecIn = thermRead(MAIN_DEV_TERM_REC_IN);
	mainContinue();
	mainTempRoomIn = thermRead(MAIN_DEV_TERM_ROOM_IN);
	mainContinue();
	mainTempRoomOut = thermRead(MAIN_DEV_TERM_ROOM_OUT);
	mainContinue();
	mainTempRecOut = thermRead(MAIN_DEV_TERM_REC_OUT);
	mainContinue();
	mainTempGwc = thermRead(MAIN_DEV_TERM_GWC);
	mainContinue();
	
	// Ustawienie trybu pracy
	if(mainModeCur != mainModeNew) {
		mainModeCur = mainModeNew;
		if(mainModeCur > 0 && mainModeCur < 4) {
			uint16_t addressTmp = address + (mainModeCur * 2) - 1;
			pwmSet(MAIN_DEV_FAN_IN,eepromReadByte(addressTmp));
			pwmSet(MAIN_DEV_FAN_OUT,eepromReadByte(addressTmp + 1));
		} else {
			pwmSet(MAIN_DEV_FAN_IN,0);
			pwmSet(MAIN_DEV_FAN_OUT,0);
		}
		messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, mainModeCur);
	}
	
	// Ustawienie bypass
	if(mainBypass && outputGet(MAIN_DEV_BYPASS) == OUTPUT_OFF) {
		outputSet(MAIN_DEV_BYPASS,OUTPUT_OPEN,0);
		messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, 0x0011);
	} else if(!mainBypass && outputGet(MAIN_DEV_BYPASS) == OUTPUT_OPEN) {
		outputSet(MAIN_DEV_BYPASS,OUTPUT_OFF,0);
		messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, 0x0010);
	}
	
	// Ustawienia GWC
	if(mainGwc && outputGet(MAIN_DEV_GWC) == OUTPUT_OFF) {
		outputSet(MAIN_DEV_GWC,OUTPUT_OPEN,0);
		outputSet(MAIN_DEV_OUTSIDE,OUTPUT_OFF,0);
		messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, 0x0021);
	} else if(!mainGwc && outputGet(MAIN_DEV_GWC) == OUTPUT_OPEN) {
		outputSet(MAIN_DEV_GWC,OUTPUT_OFF,0);
		outputSet(MAIN_DEV_OUTSIDE,OUTPUT_OPEN,0);
		messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, 0x0020);
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if(devicesTab[ndd].type == DEV_TYPE_RECUPERATOR) {
		switch(com) {
			case DECODER_REQUEST_COMMAND_11:
			mainModeNew = param1;
			break;
			case DECODER_REQUEST_COMMAND_12:
			mainBypass = param1;
			break;
			case DECODER_REQUEST_COMMAND_13:
			mainGwc = param1;
			break;
		}
	}
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {
	if(devicesTab[nsd].type == DEV_TYPE_RECUPERATOR) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		MESS_PUT_BYTE(mainModeNew);
		MESS_PUT_BYTE(mainBypass);
		MESS_PUT_BYTE(mainGwc);
		messPutWord(mainSpeedFanIn);
		messPutWord(mainSpeedFanOut);
		messPutWord(mainTempRecIn);
		messPutWord(mainTempRoomIn);
		messPutWord(mainTempRoomOut);
		messPutWord(mainTempRecOut);
		messPutWord(mainTempGwc);
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {	
}


#endif