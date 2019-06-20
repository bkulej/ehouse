#define MAIN_BODY

#include "devices.h"
#include "debug.h"
#include "main.h"
#include "pin.h"
#include "timer.h"
#include "event.h"
#include "conn.h"
#include "utils.h"
#include "lcdt.h"
#include "therm.h"
#include "channel1.h"
#include "owire.h"
#include "mess.h"
#include "gsm.h"
#include "system.h"
#include "output.h"
#include "input.h"
#include "volt.h"
#include "boot.h"
#include "decoder.h"
#include "pwm.h"
#include "freq.h"
#include "watchdog.h"
#include <stdlib.h>
#include <inttypes.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>


#if (SOFTWARE != 0x0000) && (SOFTWARE != 0x0001)

int main(void) {		
	
	// Wylaczenia watchdoga
	wdtDisable();
	
	// Wylaczenie przerwan
	cli();
	MCUSR = 0;	

	// Inicjacja debugera
	DEBUG_INIT;		
	DEBUG_S("!!INIT!!");		

#ifdef TIMER_MODULE
	timerInit();
#endif

#ifdef UTILS_MODULE
	srand( utilsCharToBits(BOOT_GET_SERIAL_PART(8),12) 
		| utilsCharToBits(BOOT_GET_SERIAL_PART(9),8)
		| utilsCharToBits(BOOT_GET_SERIAL_PART(10),4) 
		| utilsCharToBits(BOOT_GET_SERIAL_PART(11),0));
#endif

#ifdef OUTPUT_MODULE
	outputInit();
#endif

#ifdef CHANNEL1_MODULE
	channel1Init();	
#endif

#ifdef LCDT_MODULE
	lcdtInit();
#endif	

#ifdef THERM_MODULE
	thermInit();
#endif	

#ifdef INPUT_MODULE
	inputInit();
#endif

#ifdef PWM_MODULE
	pwmInit();
#endif

#ifdef VOLT_MODULE
	voltInit();
#endif

#ifdef FREQ_MODULE
	freqInit();
#endif
 
#ifdef GSM_MODULE
	gsmInit();
#endif	
	
	// Wlaczenie Watchdoga
	wdtEnable();
	
	// W³aczenie przerwañ
	sei();

	DEBUG_S("!!START!!");
	
#ifdef MESS_MODULE
	messSystemSend(DECODER_SYSTEM_INFO,0x00,0xFFFF);
#endif
	
#ifdef DEVICE_MAIN
	mainInit();
#endif	

#ifdef SYSTEM_MODULE
	systemExecuteMakro(0xFFFF);
	devicesTab[0].value = 0xFFFF;
#endif
	
	// Pêtla g³owna
	while(1) {
		
		//wdt_reset();
			
		mainContinue();

#ifdef DEVICE_MAIN
		mainThread();
#endif
	}		
}


void mainContinue() {
			
#ifdef OUTPUT_MODULE
	// Obs³uga komunikatów
	if(eventCheck(EVENT_OUTPUT)){
		outputEvent();
	}
#endif

#ifdef INPUT_MODULE
	// Obs³uga komunikatów
	if(eventCheck(EVENT_INPUT)){
		inputEvent();
	}
#endif

#ifdef CONN_MODULE
	// Obs³uga kanalu 0
	if(eventCheck(EVENT_CONN)){
		connEvent();
	}
#endif
			
#ifdef CHANNEL1_MODULE
	// Obs³uga kanalu 1
	if(eventCheck(EVENT_CHANNEL1)){
		connReceive();
		channel1RxClear();
	}
#endif

#ifdef THERM_MODULE
	// Obs³uga komunikatów
	if(eventCheck(EVENT_THERM)){
		thermEvent();
	}
#endif

#ifdef VOLT_MODULE
	// Obs³uga komunikatów
	if(eventCheck(EVENT_VOLT)){
		voltEvent();
	}
#endif

#ifdef GSM_MODULE
	// Obs³uga komunikatów
	if(eventCheck(EVENT_GSM)){
		gsmEvent();
	}
#endif

}

#endif
