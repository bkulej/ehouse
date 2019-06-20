#include "../devices.h"
#include "../debug.h"
#include "../utils.h"
#include "../therm.h"
#include "../pin.h"
#include "../decoder.h"
#include "../output.h"
#include "../utils.h"
#include "../eeprom.h"
#include "../mess.h"
#include "../timer.h"
#include "../channel1.h"
#include "../main.h"
#include <inttypes.h>

/********************************************************************************************
Sterownik ogrzewania
Konfiguracja urzadzenia
0 DEVICE(1) urzadzenie przypisane do konfiguracji
1 - Temperatura kaloryfera
2 - /
3 - Temperatura pod³ogi
4 - /
5 - Temperatura wody
6 - /
7 - Adres sterownika pod³ogi
8 - /
9 - Adres sterownika pieca
A - /
********************************************************************************************/

// ATMEGA328
#if SOFTWARE == 0x0005 && defined (DEVICE_MAIN)

#define MAIN_TEMP_WATER_DIFF		50	// Temperatura * 10 ; 50 = 5C
#define MAIN_TEMP_FLOOR_DIFF		100
#define MAIN_TEMP_BOILER_WATER		800

#define MAIN_DEV_MAIN				1	// Numer sterownika zaworu
#define MAIN_DEV_TEMP_BOILER_INP	2	// Numer termometru wejœciowego
#define MAIN_DEV_TEMP_WATER_OUT		3	// Numer termometru wyjœciowego
#define MAIN_DEV_PUMP_RADIATOR		4	// Numer urz¹dzenia pompy kaloryferów
#define MAIN_DEV_PUMP_WATER			5	// Numer urz¹dzenia pompy wody
#define MAIN_DEV_ELECTRIC_WATER		6	// Numer urz¹dzenia grzalki wody

#define MAIN_OFFSET_TEMP_RADIATOR	0x01
#define MAIN_OFFSET_TEMP_FLOOR		0x03
#define MAIN_OFFSET_TEMP_WATER		0x05
#define MAIN_OFFSET_ADDR_FLOOR		0x07
#define MAIN_OFFSET_ADDR_BOILER		0x09
#define MAIN_OFFSET_WATER_MODE		0x0B

#define MAIN_MODE_HEATING			0
#define MAIN_MODE_WATHER			1
#define MAIN_MODE_ALARM				2

#define MAIN_WATER_ELECTRIC			0
#define MAIN_WATER_PUMP				1


volatile uint8_t mainMode;
volatile uint8_t mainAlarm;
volatile uint16_t mainConf;
volatile int16_t mainRadiatorTempSet;
volatile int16_t mainFloorTempSet;
volatile int16_t mainFloorTempGet;
volatile uint8_t mainFloorStatus;
volatile int16_t mainBoilerTempSet;
volatile int16_t mainBoilerTempGet;
volatile int16_t mainWaterTempSet;
volatile int16_t mainBoilerTempInp;
volatile int16_t mainWaterTempOut;
volatile uint8_t mainWaterMode;

struct {
	uint8_t  wait;
	uint8_t	 id;
	uint16_t add;
	uint8_t  dev;
	uint8_t  com;
	uint8_t  param1;
	uint8_t  param2;
} mainMess;


void mainSend(uint16_t conf, uint8_t com, uint8_t param1, uint8_t param2) {
	mainMess.id = messGetId();
	mainMess.add = eepromReadWord(conf);
	mainMess.dev = MAIN_DEV_MAIN;
	mainMess.com = com;
	mainMess.wait = 1;
	messAddressSend(MESS_MODE_REQUEST|MESS_MODE_END,mainMess.add,mainMess.dev,mainMess.id,mainMess.com,param1,param2);
	while(mainMess.wait) {
		mainContinue();
	}
}


uint8_t mainFloorSet(int16_t temp) {
	if(mainFloorTempGet == temp) {
		return 1;
	}
	mainSend(mainConf + MAIN_OFFSET_ADDR_FLOOR,DECODER_REQUEST_COMMAND_11, temp >> 8, temp & 0xFF);
	if(mainMess.com == DECODER_RESPONSE_OK) {
		mainFloorTempGet = mainMess.param1 << 8 | mainMess.param2;
		mainFloorStatus =  mainFloorTempGet != 0;
		return 1;
	} else {
		mainFloorTempGet = THERM_NI;
		mainFloorStatus = 2;
		return 0;
	}
}


uint8_t mainBoilerSet(int16_t temp) {
	if(mainBoilerTempGet == temp) {
		return 1;
	}
	mainSend(mainConf + MAIN_OFFSET_ADDR_BOILER,DECODER_REQUEST_COMMAND_11, temp >> 8, temp & 0xFF);
	if(mainMess.com == DECODER_RESPONSE_OK) {
		mainBoilerTempGet = mainMess.param1 << 8 | mainMess.param2;
		return 1;
	} else {
		return 0;
	}
}


uint8_t mainRadiatorSet(int16_t temp) {
	if(temp == 0 && outputGet(MAIN_DEV_PUMP_RADIATOR) != OUTPUT_OFF) {
		outputSet(MAIN_DEV_PUMP_RADIATOR, OUTPUT_OFF,0);
	} else if(temp > 0 && outputGet(MAIN_DEV_PUMP_RADIATOR) != OUTPUT_OPEN) {
		outputSet(MAIN_DEV_PUMP_RADIATOR, OUTPUT_OPEN,0);
	}
	return 1;
}


uint8_t mainWaterElectricSet(int16_t temp) {
	if(temp == 0 && outputGet(MAIN_DEV_ELECTRIC_WATER) != OUTPUT_OFF) {
		outputSet(MAIN_DEV_ELECTRIC_WATER, OUTPUT_OFF,0);
	} else if(temp > 0 && outputGet(MAIN_DEV_ELECTRIC_WATER) != OUTPUT_OPEN) {
		outputSet(MAIN_DEV_ELECTRIC_WATER, OUTPUT_OPEN,0);
	}
	return 1;
}


uint8_t mainWaterPumpSet(int16_t temp) {
	if(temp == 0 && outputGet(MAIN_DEV_PUMP_WATER) != OUTPUT_OFF) {
		outputSet(MAIN_DEV_PUMP_WATER, OUTPUT_OFF,0);
	} else if(temp > 0 && outputGet(MAIN_DEV_PUMP_WATER) != OUTPUT_OPEN) {
		outputSet(MAIN_DEV_PUMP_WATER, OUTPUT_OPEN,0);
	}
	return 1;
}


void mainHeatingOn() {
	// Wylacz grzalke
	mainWaterElectricSet(0);
	// Wylacz pompe wody
	mainWaterPumpSet(0);
	// Ustaw temperature na piecu
	mainBoilerSet(mainBoilerTempSet);
	// Wlacz kaloryfery
	mainRadiatorSet(mainRadiatorTempSet);
	// Ustaw temperaturê pod³ogówki
	mainFloorSet(mainFloorTempSet);
}


uint8_t mainAlarmOn() {
	 // Wylacz grzalke
	 mainWaterElectricSet(0);
	 // Ustaw maksymalna temperature na piecu
	 if(!mainBoilerSet(MAIN_TEMP_BOILER_WATER)) {
		 return 0;
	 }
	 // Wlacz pompe wody
	 if(!mainWaterPumpSet(MAIN_TEMP_BOILER_WATER)) {
		 return 0;
	 }
	 // Zresetuj alarm
	 mainAlarm = 0;
	 timerSetWait(60);
	 return 1;
}


uint8_t mainWatherOn() {
	if(mainWaterMode == MAIN_WATER_ELECTRIC) {		
		// Wylacz pompe wody
		mainWaterPumpSet(0);
		// Ustaw temperature na piecu
		mainBoilerSet(mainBoilerTempSet);
		// Wlacz kaloryfery
		mainRadiatorSet(mainRadiatorTempSet);
		// Ustaw temperaturê pod³ogówki
		mainFloorSet(mainFloorTempSet);
		// Wlacz grzalke
		if(!mainWaterElectricSet(mainWaterTempSet)) {
			return 0;
		}
	} else {		
		// Wy³¹cz grza³kê
		mainWaterElectricSet(0);
		// Wylacz podlogowke
		if(!mainFloorSet(0)) {
			return 0;
		}
		// Wylacz kaloryfery
		if(!mainRadiatorSet(0)) {
			return 0;
		}
		// Ustaw maksymalna temperature na piecu
		if(!mainBoilerSet(MAIN_TEMP_BOILER_WATER)) {
			return 0;
		}
		// Wlacz pompe wody
		if(!mainWaterPumpSet(mainWaterTempSet)) {
			return 0;
		}
	}
	return 1;
}




void mainInit() {
	mainMess.wait = 0;
	mainBoilerTempGet = THERM_NI;
	mainFloorTempGet = THERM_NI;
	mainMode = MAIN_MODE_HEATING;
	mainAlarm = 0;
	outputSet(MAIN_DEV_PUMP_RADIATOR, OUTPUT_OFF,0);
	outputSet(MAIN_DEV_PUMP_WATER, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_ELECTRIC_WATER, OUTPUT_OFF, 0);
}


void mainThread() {
	
	// Opoznienie
	if(timerCheckWait()) return;
	timerSetWait(1);
	
	// Pobranie temperatur
	mainBoilerTempInp = thermRead(MAIN_DEV_TEMP_BOILER_INP);
	mainContinue();
	mainWaterTempOut = thermRead(MAIN_DEV_TEMP_WATER_OUT);
	mainContinue();
	
	// Sprawdzenie konfiga
	mainConf = devicesTab[MAIN_DEV_MAIN].config << 4;
	if(eepromReadByte(mainConf) != MAIN_DEV_MAIN) {
		return;
	}
	
	// Pobranie ustawieñ
	mainRadiatorTempSet = eepromReadWord(mainConf + MAIN_OFFSET_TEMP_RADIATOR);
	mainFloorTempSet  = eepromReadWord(mainConf + MAIN_OFFSET_TEMP_FLOOR);
	mainWaterTempSet  = eepromReadWord(mainConf + MAIN_OFFSET_TEMP_WATER);
	mainWaterMode  = eepromReadByte(mainConf + MAIN_OFFSET_WATER_MODE);
	
	// Obliczenie temperatury pieca
	if(mainRadiatorTempSet != 0) {
		mainBoilerTempSet = mainRadiatorTempSet;
	} else if(mainFloorTempSet != 0) {
		mainBoilerTempSet = mainFloorTempSet + MAIN_TEMP_FLOOR_DIFF;
	} else {
		mainBoilerTempSet = 0;
	}
	
	// Sprawdzenie czy pojawi³ siê alarm
	if(mainAlarm) {
		if(!mainAlarmOn()) {
			return;
		}
		// Przelacz na tryb alarm
		mainMode = MAIN_MODE_ALARM;
		messSystemSend(DECODER_SYSTEM_INFO,MAIN_DEV_MAIN,MAIN_MODE_ALARM);
		 
	// Sprawdzenie czy zakonczy³ siê alarm
	} else if(mainMode == MAIN_MODE_ALARM && !mainAlarm) {
		// Zresetuj alarm
		mainMode = MAIN_MODE_HEATING;
		messSystemSend(DECODER_SYSTEM_INFO,MAIN_DEV_MAIN,MAIN_MODE_HEATING);
		 
	// Sprawdzenie czy temperatura wody nie spadla o zadana roznice
	} else if(mainMode == MAIN_MODE_HEATING  && (mainWaterTempSet - mainWaterTempOut) > MAIN_TEMP_WATER_DIFF && mainWaterTempSet != 0) {
		if(!mainWatherOn()) {
			return;
		}	
		// Przelacz na tryb WATHER
		mainMode = MAIN_MODE_WATHER;
		messSystemSend(DECODER_SYSTEM_INFO,MAIN_DEV_MAIN,MAIN_MODE_WATHER);
		
	// Sprawdzenie czy wy³¹czyc ogrzewanie wody
	} else if(mainMode == MAIN_MODE_WATHER && (mainWaterTempOut > mainWaterTempSet || mainWaterTempSet == 0)) {
		//Przelacz na tryb HEATING
		mainMode = MAIN_MODE_HEATING;
		messSystemSend(DECODER_SYSTEM_INFO,MAIN_DEV_MAIN,MAIN_MODE_HEATING);
		
	// Czy normalny tryb
	} else if(mainMode == MAIN_MODE_HEATING) {
		mainHeatingOn();
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if(devicesTab[ndd].type == DEV_TYPE_HEAT_MAIN) {
		switch(com) {
			case DECODER_REQUEST_COMMAND_11:
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_RADIATOR, param1);
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_RADIATOR + 1,param2);
				break;
			case DECODER_REQUEST_COMMAND_12:
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_FLOOR, param1);
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_FLOOR + 1,param2);
				break;
			case DECODER_REQUEST_COMMAND_13:
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_WATER, param1);
				eepromWriteByte(mainConf + MAIN_OFFSET_TEMP_WATER + 1,param2);
				break;
			case DECODER_REQUEST_COMMAND_14:
				
				break;
			case DECODER_REQUEST_COMMAND_15:
			
				break;
			break;
		}
	} else if(com == DECODER_SYSTEM_ALARM) {
		uint16_t alarmCode = param1 << 8 | param2;
		if(alarmCode == DECODER_ALARM_BOLIER_TEMP_HIGH) {
			mainAlarm = 1;
		}
	}	
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {
	if(devicesTab[nsd].type == DEV_TYPE_HEAT_MAIN) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		messPutWord(mainRadiatorTempSet);
		messPutWord(mainFloorTempSet);
		messPutWord(mainWaterTempSet);
		messPutWord(mainBoilerTempInp);
		messPutWord(mainWaterTempOut);
		MESS_PUT_BYTE(outputGet(MAIN_DEV_PUMP_RADIATOR));
		MESS_PUT_BYTE(mainFloorStatus);
		MESS_PUT_BYTE(outputGet(MAIN_DEV_ELECTRIC_WATER) | outputGet(MAIN_DEV_PUMP_WATER));
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {			
	if(mainMess.add == asd && mainMess.dev == ndd && mainMess.id == id) {
		mainMess.com = com;
		mainMess.param1 = param1;
		mainMess.param2 = param2;
		mainMess.wait = 0;
		mainMess.com = 0;
	}
}


#endif
