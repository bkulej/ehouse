#include <inttypes.h>

#ifndef VERSION_HEADER

	#define VERSION_HEADER
	
	#define F_CPU 				16000000UL
	#define MCU_0164	defined (__AVR_ATmega164__) || defined (__AVR_ATmega164A__) || defined (__AVR_ATmega164P__) || defined (__AVR_ATmega164PA__) 
	#define MCU_0324 defined (__AVR_ATmega324__) || defined (__AVR_ATmega324A__) || defined (__AVR_ATmega324P__) || defined (__AVR_ATmega324PA__)
	#define MCU_0644 defined (__AVR_ATmega644__) || defined (__AVR_ATmega644A__) || defined (__AVR_ATmega644P__) || defined (__AVR_ATmega644PA__)
	#define MCU_XXX4 MCU_0164 || MCU_0324 || MCU_0644
	#define MCU_0088	defined (__AVR_ATmega88__) || defined (__AVR_ATmega88A__) || defined (__AVR_ATmega88P__) || defined (__AVR_ATmega88PA__)
	#define MCU_0168 defined (__AVR_ATmega168__) || defined (__AVR_ATmega168A__) || defined (__AVR_ATmega168P__) || defined (__AVR_ATmega168PA__)
	#define MCU_0328 defined (__AVR_ATmega328__) || defined (__AVR_ATmega328A__) || defined (__AVR_ATmega328P__) || defined (__AVR_ATmega328PA__)		
	#define MCU_XXX8 MCU_0088 || MCU_0168 || MCU_0328

	
	//------------------------------TYPE----DESCRIPTION---------------------PARAM1----------PARAM2
	#define DEV_TYPE_SYSTEM			0x00	// Urzadzenie systemowe			PAGE_CONF_BEG	PAGE_CONF_END
	#define DEV_TYPE_OUTPUT1		0x01	// Wyjscie pojedyncze			PIN_ON			PIN_OFF
	#define DEV_TYPE_OUTPUT2		0x02	// Wyjscie podwojne pierwsze	PIN_ON			PIN_OFF
	#define DEV_TYPE_THERM			0x03	// Termometr					PIN_IN			-
	#define DEV_TYPE_VOLT			0x04	// Woltomierz					PIN_IN			STATUS(0x02 lub 0x08)
	#define DEV_TYPE_INPUT			0x05	// Wejscie						PIN_IN			PULLUP(0x01)
	#define DEV_TYPE_PWM			0x06	// PWM							PIN_IN			PRESCALER (0x01=1;0x02=8; 0x03=64;0x04=256;0x05=1024)
	#define DEV_TYPE_FREQ			0x07	// Miernik czêstotliwoœci
		
	#define DEV_TYPE_MAIN_BEGIN		0x20	// Od tego numeru zaczynaj¹ siê urz¹dzenia obs³ugiwane przez MAIN
	
	#define DEV_TYPE_WATCHDOG		0x21	// Watchdog sender
	
	#define DEV_TYPE_HEAT_MAIN		0x30	// Sterownik pieca
	#define DEV_TYPE_HEAT_3WAY		0x31	// Zawór 3 drogowy
	#define DEV_TYPE_HEAT_4WAY		0x32	// Zawór 4 drogowy
	#define DEV_TYPE_IRRIGATION		0x33	// Sterownik podlewania
	#define DEV_TYPE_RECUPERATOR	0x34	// Sterownik rekuperatora
	#define DEV_TYPE_GATE1			0x35	// Brama wjazdowa z funkcjami stop,pp,open,close
	#define DEV_TYPE_GATE2			0x36	// Brama gara¿owan z funkcj¹ pp
	
	#define DEV_TYPE_NULL			0xFF	// Brak urzadzenia	
	
	typedef struct {			
			uint8_t type;
			uint8_t param1;
			uint8_t param2;		
			uint8_t config;
			uint16_t value;						
			uint16_t timer;				
	} DeviceStruct;	
	
	#ifndef DEVICES_BODY		
		extern int16_t deviceRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2);
		extern void deviceResponseSend(uint8_t id, uint16_t add, uint8_t nsd,  uint8_t requestCommand, int16_t requestResult);		
	#endif
	
#endif


//ALL, Bootloader
#if SOFTWARE == 0x0000
	#define CRC8_MODULE
#endif


//Watchdog
#if SOFTWARE == 0x0001
	#define CRC8_MODULE
#endif
	
	
//Standardowy IN/OUT z termometrem i voltomierzem
#if (SOFTWARE == 0x0002)  && (MCU_0328)

	#define HARDWARE 		0x0328
	
	#define DEV_COUNT  		11

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE
	#define VOLT_MODULE

	#ifdef MAIN_BODY					
		DeviceStruct devicesTab[DEV_COUNT+1] = {				
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 - 
			{ DEV_TYPE_INPUT,	0xC0,	0x10}, // 1 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC1,	0x10}, // 2 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC2,	0x10}, // 3 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC3,	0x10}, // 4 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC4,	0x10}, // 5 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC5,	0x11}, // 6 - Input aktywne 1 dla alarmu
			{ DEV_TYPE_OUTPUT1,	0xB1,	0xB2}, // 7 - 
			{ DEV_TYPE_OUTPUT1,	0xB0,	0xD7}, // 8 -
			{ DEV_TYPE_OUTPUT1,	0xD6,	0xD5}, // 9 - 		
			{ DEV_TYPE_THERM,	0xD2,	0x00}, // A -
			{ DEV_TYPE_VOLT,	0xD3,	0x01}  // B -
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];		
	#endif

#endif


//Sterownik zaworu 3-drogowego
#if (SOFTWARE == 0x0003) && (MCU_0168)
		
	#define HARDWARE 		0x0168
	
	#define DEV_COUNT  		6

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define THERM_MODULE
	#define OWIRE_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE------------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x1F}, // Wszystkie urzadzenia
			{ DEV_TYPE_HEAT_3WAY,	0x00,	0x00}, // 1 - zawór 3 drogowy	
			{ DEV_TYPE_THERM,		0xC0,	0x00}, // 2 - temperatura wyjsciowa
			{ DEV_TYPE_THERM,		0xC1,	0x00}, // 3 - temperatura powrotu
			{ DEV_TYPE_OUTPUT1,		0xB1,	0xB2}, // 4 - pompa
			{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // 5 - otworzenie zaworu
			{ DEV_TYPE_OUTPUT2,		0xD6,	0xD5}, //     zamkniecie zaworu
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif
	
#endif


//Sterownik zaworu 4-ro drogowego
#if (SOFTWARE == 0x0004) && (MCU_0168)

	#define HARDWARE 		0x0168

	#define DEV_COUNT  		8

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define THERM_MODULE
	#define OWIRE_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x1F}, // Wszystkie urzadzenia
			{ DEV_TYPE_HEAT_4WAY,	0x00,	0x00}, // 1 - zawór 4 drogowy
			{ DEV_TYPE_THERM,		0xC0,	0x00}, // 2 - temperatura wyjsciowa pieca
			{ DEV_TYPE_THERM,		0xC1,	0x00}, // 3 - temperatura wyjsciowa ogrzewania
			{ DEV_TYPE_THERM,		0xC2,	0x00}, // 4 - temperatura powrotu ogrzewania
			{ DEV_TYPE_THERM,		0xC3,	0x00}, // 5 - temperatura powrotu pieca
			{ DEV_TYPE_OUTPUT1,		0xB1,	0xB2}, // 6 - piec
			{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // 7 - otworzenie zaworu
			{ DEV_TYPE_OUTPUT2,		0xD6,	0xD5}, //     zamkniecie zaworu
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik ogrzewania
#if (SOFTWARE == 0x0005) && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		6

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define THERM_MODULE
	#define OWIRE_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x3F}, // Wszystkie urzadzenia
			{ DEV_TYPE_HEAT_MAIN,	0x00,	0x00}, // 1 - sterownik ogrzewania
			{ DEV_TYPE_THERM,		0xC0,	0x00}, // 2 - temperatura wejœciowa
			{ DEV_TYPE_THERM,		0xC1,	0x00}, // 3 - temperatura wody
			{ DEV_TYPE_OUTPUT1,		0xB1,	0xB2}, // 4 - pompa kaloryferów
			{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // 5 - pompa wody
			{ DEV_TYPE_OUTPUT1,		0xD6,	0xD5}  // 6 - grzalka wody
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik podlewania
#if (SOFTWARE == 0x0006) && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		8

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x3F}, // Wszystkie urzadzenia
			{ DEV_TYPE_IRRIGATION,	0x00,	0x00}, // 1 - sterownik
			{ DEV_TYPE_OUTPUT1,		0xB1,	0xB2}, // 2 - sekcja 1
			{ DEV_TYPE_OUTPUT1,		0xC4,	0xC5}, // 3 - sekcja 2
			{ DEV_TYPE_OUTPUT1,		0xD3,	0xD2}, // 4 - sekcja 3
			{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // 5 - sekcja 4
			{ DEV_TYPE_OUTPUT1,		0xD6,	0xD5}, // 6 - sekcja 5
			{ DEV_TYPE_OUTPUT1,		0xC0,	0xC1}, // 7 - sekcja 6
			{ DEV_TYPE_OUTPUT1,		0xC2,	0xC3}  // 8 - sekcja 7
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik rekuperatora
#if (SOFTWARE == 0x0007) && (MCU_0644)

	#define HARDWARE 		0x0644

	#define DEV_COUNT  		14

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE
	#define PWM_MODULE
	#define FREQ_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x3F}, // Wszystkie urzadzenia
			{ DEV_TYPE_RECUPERATOR,	0x00,	0x00}, // 1 - sterownik
			{ DEV_TYPE_OUTPUT1,		0xA0,	0xA1}, // 2 - bypass
			{ DEV_TYPE_OUTPUT1,		0xA2,	0xA3}, // 3 - czerpnia
			{ DEV_TYPE_OUTPUT1,		0xA4,	0xA5}, // 4 - gwc
			{ DEV_TYPE_OUTPUT1,		0xA6,	0xA7}, // 5 - 
			{ DEV_TYPE_PWM,			0xB3,	0x02}, // 6 - wentylator IN
			{ DEV_TYPE_PWM,			0xB4,	0x02}, // 7 - wentylator OUT
			{ DEV_TYPE_FREQ,		0xB1,	0x00}, // 8 - pomiar obrotów silników
			{ DEV_TYPE_OUTPUT1,		0xB0,	0x00}, // 9 - selektor silnika
			{ DEV_TYPE_THERM,		0xC0,	0x00}, // A - termometr
			{ DEV_TYPE_THERM,		0xC1,	0x00}, // B - termometr
			{ DEV_TYPE_THERM,		0xC2,	0x00}, // C - termometr
			{ DEV_TYPE_THERM,		0xC3,	0x00}, // D - termometr
			{ DEV_TYPE_THERM,		0xC4,	0x00}  // E - termometr	
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik bramy wjazdowej
#if (SOFTWARE == 0x0008)  && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		9

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 - 
			{ DEV_TYPE_GATE1,	0x00,	0x00}, // 1 - Sterownik bramy
			{ DEV_TYPE_OUTPUT1,	0xC5,	0x00}, // 2 - Stop
			{ DEV_TYPE_OUTPUT1,	0xC4,	0x00}, // 3 - PP
			{ DEV_TYPE_OUTPUT1,	0xC3,	0x00}, // 4 - Open
			{ DEV_TYPE_OUTPUT1,	0xC2,	0x00}, // 5 - Close
			{ DEV_TYPE_INPUT,	0xD2,	0x10}, // 6 - Czujnik otwarcia
			{ DEV_TYPE_INPUT,	0xD3,	0x10}, // 7 - Dzwonek do bramy
			{ DEV_TYPE_INPUT,	0xC0,	0x10}, // 8 - Sygna³ otwarcia
			{ DEV_TYPE_INPUT,	0xC1,	0x10}  // 9 - Alarm systemowy
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik bramy gara¿owej z temperatura i czujnikiem zmierzchowym
#if (SOFTWARE == 0x0009)  && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		11

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE
	#define VOLT_MODULE
	#define DEVICE_MAIN

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 - 
			{ DEV_TYPE_GATE2,	0x00,	0x00}, // 1 - Sterownik bramy 
			{ DEV_TYPE_OUTPUT1,	0xB1,	0x00}, // 2 - Wicket
			{ DEV_TYPE_OUTPUT1,	0xB2,	0x00}, // 3 - PP
			{ DEV_TYPE_OUTPUT1,	0xB0,	0x00}, // 4 - Open
			{ DEV_TYPE_OUTPUT1,	0xD7,	0x00}, // 5 - Close 
			{ DEV_TYPE_INPUT,	0xD2,	0x10}, // 6 - Czujnik otwarcia
			{ DEV_TYPE_OUTPUT1,	0xC0,	0xC1}, // 7 - Zasilanie kamery 
			{ DEV_TYPE_OUTPUT1,	0xD6,	0xD5}, // 8 - Zasilanie czujki alarmowej
			{ DEV_TYPE_INPUT,	0xD3,	0x11}, // 9 - Czujnik alarmu
			{ DEV_TYPE_THERM,	0xC5,	0x00}, // A - Czujnik temperatur
			{ DEV_TYPE_VOLT,	0xC4,	0x01}  // B - Czujnik swiatla
				
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik odbiornika radiowego
#if (SOFTWARE == 0x000A)  && (MCU_0328)

	#define HARDWARE 		0x0328
	
	#define DEV_COUNT  		13

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE

	#ifdef MAIN_BODY					
		DeviceStruct devicesTab[DEV_COUNT+1] = {				
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 - 
			{ DEV_TYPE_INPUT,	0xC0,	0x10}, // 1 - 
			{ DEV_TYPE_INPUT,	0xC1,	0x10}, // 2 - 
			{ DEV_TYPE_INPUT,	0xC2,	0x10}, // 3 - 
			{ DEV_TYPE_INPUT,	0xC3,	0x10}, // 4 - 
			{ DEV_TYPE_INPUT,	0xC4,	0x10}, // 5 - Brama garazowa
			{ DEV_TYPE_INPUT,	0xC5,	0x10}, // 6 - Brama wjazdowa
			{ DEV_TYPE_OUTPUT1,	0xB1,	0xB2}, // 7 - Oswietlenie podjazdu
			{ DEV_TYPE_OUTPUT1,	0xB0,	0xD7}, // 8 - Oswietlenie garazu
			{ DEV_TYPE_OUTPUT1,	0xD6,	0xD5}, // 9 - Zasilanie czujki ruchu 		
			{ DEV_TYPE_THERM,	0xD2,	0x00}, // A - Termometr w gara¿u
			{ DEV_TYPE_INPUT,	0xD3,	0x11}, // B - Czujka ruchu
			{ DEV_TYPE_INPUT,	0xB3,	0x10}, // C - Swiatlo na zewnatrz
			{ DEV_TYPE_INPUT,	0xB4,	0x10}  // D - Swiatlo w garazu
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];		
	#endif

#endif

//Sterownik rolet z termometrem i czujnikiem swiatla
#if (SOFTWARE == 0x000B)  && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		11

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE
	#define VOLT_MODULE

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 -
			{ DEV_TYPE_INPUT,	0xC0,	0x10}, // 1 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC1,	0x10}, // 2 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC2,	0x10}, // 3 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC3,	0x10}, // 4 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC4,	0x10}, // 5 - Input aktywne 0 dla przycisków
			{ DEV_TYPE_INPUT,	0xC5,	0x11}, // 6 - Input aktywne 1 dla czujki alarmowej
			{ DEV_TYPE_OUTPUT1,	0xB1,	0xB2}, // 7 -
			{ DEV_TYPE_OUTPUT2,	0xB0,	0xD7}, // 8 -
			{ DEV_TYPE_OUTPUT1,	0xD6,	0xD5}, // 9 -
			{ DEV_TYPE_THERM,	0xD2,	0x00}, // A - Termometr
			{ DEV_TYPE_VOLT,	0xD3,	0x01}  // B - Woltomierz
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif

//Sterownik 8 przekaznikow
#if (SOFTWARE == 0x000C)  && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		9

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define OWIRE_MODULE
	#define THERM_MODULE
	#define VOLT_MODULE

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE--------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,	0x02,	0x3F}, // 0 -
			{ DEV_TYPE_OUTPUT1,	0xB5,	0xB3}, // 1 -
			{ DEV_TYPE_OUTPUT1,	0xD3,	0xD2}, // 2 -
			{ DEV_TYPE_OUTPUT1,	0xC4,	0xC5}, // 3 -
			{ DEV_TYPE_OUTPUT1,	0xC2,	0xC3}, // 4 -
			{ DEV_TYPE_OUTPUT1,	0xC0,	0xC1}, // 5 -
			{ DEV_TYPE_OUTPUT1,	0xD6,	0xD5}, // 6 -
			{ DEV_TYPE_OUTPUT1,	0xB0,	0xD7}, // 7 -
			{ DEV_TYPE_OUTPUT1,	0xB1,	0xB2}  // 8 -
			
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif


//Sterownik 4 przeka¿ników
#if (SOFTWARE == 0x000D)  && (MCU_0328)

	#define HARDWARE 		0x0328

	#define DEV_COUNT  		10

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE
	#define PIN_MODULE
	#define OUTPUT_MODULE
	#define INPUT_MODULE

	#ifdef MAIN_BODY
		DeviceStruct devicesTab[DEV_COUNT+1] = {
			//TYPE------------------PARAM1--PARAM2
			{ DEV_TYPE_SYSTEM,		0x02,	0x3F}, // 0 -
			{ DEV_TYPE_INPUT,		0xD2,	0x10}, // 1 - 
			{ DEV_TYPE_INPUT,		0xD3,	0x10}, // 2 -	
			{ DEV_TYPE_INPUT,		0xC5,	0x10}, // 3 -	
			{ DEV_TYPE_INPUT,		0xC4,	0x10}, // 4 -	
			{ DEV_TYPE_THERM,		0xB2,	0x10}, // 5 -	
			{ DEV_TYPE_VOLT,		0xB1,	0x10}, // 6 -	
			{ DEV_TYPE_OUTPUT1,		0xC2,	0xC3}, // 7 -
			{ DEV_TYPE_OUTPUT1,		0xC0,	0xC1}, // 8 - 
			{ DEV_TYPE_OUTPUT1,		0xD6,	0xD5}, // 9 - 
			{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // A - 
		};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif


//Sterownik testowy
#if (SOFTWARE == 0x1000)  && (MCU_0088)

	#define HARDWARE 		0x0088

	#define DEV_COUNT  		0

	#define WDT_MODULE
	#define UTILS_MODULE
	#define EEPROM_MODULE
	#define TIMER_MODULE
	#define EVENT_MODULE
	#define CHANNEL1_MODULE
	#define CRC8_MODULE
	#define MESS_MODULE
	#define CONN_MODULE
	#define ADDRESS_MODULE
	#define DECODER_MODULE
	#define SYSTEM_MODULE

	#ifdef MAIN_BODY
	DeviceStruct devicesTab[DEV_COUNT+1] = {
		//TYPE------------------PARAM1--PARAM2
		{ DEV_TYPE_SYSTEM,		0x02,	0x3F}, // 0 -
	};
	#else
		extern DeviceStruct devicesTab[DEV_COUNT+1];
	#endif

#endif