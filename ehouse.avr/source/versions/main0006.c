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
#include <inttypes.h>

/********************************************************************************************
Sterownik podlewania
Obs³ugiwane komendy:
10 - get status
11 - set status

Konfiguracja urzadzenia:
0 DEVICE(1) urzadzenie przypisane do konfiguracji
1 - Sekcja 1 czas podlewania w minutach
2 - Sekcja 2 czas podlewania w minutach
3 - Sekcja 3 czas podlewania w minutach
4 - Sekcja 4 czas podlewania w minutach
5 - Sekcja 5 czas podlewania w minutach
6 - Sekcja 6 czas podlewania w minutach
7 - Sekcja 7 czas podlewania w minutach
8 - Sekcja 8 czas podlewania w minutach
********************************************************************************************/

// ATMEGA88
#if SOFTWARE == 0x0006 && defined (DEVICE_MAIN)

#define MAIN_DEV_MAIN		1	// Sterownik
#define MAIN_DEV_SECT1		2	// Sekcja 1
#define MAIN_DEV_SECT2		3	// Sekcja 2
#define MAIN_DEV_SECT3		4	// Sekcja 3
#define MAIN_DEV_SECT4		5	// Sekcja 4
#define MAIN_DEV_SECT5		6	// Sekcja 5
#define MAIN_DEV_SECT6		7	// Sekcja 6
#define MAIN_DEV_SECT7		8	// Sekcja 7
#define MAIN_DEV_SECT8		9	// Sekcja 8

volatile uint8_t mainModeNew;
volatile uint8_t mainModeCurr;

void mainInit() {
	outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
	mainModeNew = 0;
	mainModeCurr = 0;
}


void mainThread() {
	
	// Czekaj okreœlony czas
	if(timerCheckWait()) return;
	
	// Czy zmieni³ siê stan
	if(mainModeCurr == mainModeNew) {
		return;
	} else {
		mainModeCurr = mainModeNew;
	}
	
	// Info o stanie
	messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, mainModeCurr);
	
	// Sprawdzenie urzadzenia
	uint16_t address = devicesTab[MAIN_DEV_MAIN].config << 4;
	if(eepromReadByte(address) != MAIN_DEV_MAIN) {
		return;
	}
	
	// Wykonanie instrukcji
	switch(mainModeCurr) {
		
		// Sekcja 1
		case 1: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 2
		case 2: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 3
		case 3: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 4
		case 4: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 5
		case 5: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 6
		case 6: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 7
		case 7: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OPEN, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			break;
		}
		
		// Sekcja 8
		case 8: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OPEN, 0);
			break;
		}
		
		// Wylaczenie wszystkich sekcji
		default: {
			outputSet(MAIN_DEV_SECT1, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT2, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT3, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT4, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT5, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT6, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT7, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_SECT8, OUTPUT_OFF, 0);
			mainModeNew = 0;
			mainModeCurr = 0;
			return;
		}
	}
	
	// Ustawienie czasu oczekiwania w sekundach
	timerSetWait(eepromReadByte(address + mainModeCurr) * 60);
	
	// Ustal nowy stan
	mainModeNew = mainModeCurr + 1;
	if(mainModeNew > 8) {
		mainModeNew = 0;
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if((devicesTab[ndd].type == DEV_TYPE_IRRIGATION) && (com == DECODER_REQUEST_COMMAND_11)) {
		mainModeNew =  param1;
		timerSetWait(0);
	}
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {
	if(devicesTab[nsd].type == DEV_TYPE_IRRIGATION) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		MESS_PUT_BYTE(mainModeNew);
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
}


#endif