//Sterownik rekuperatora
#if (SOFTWARE == 0x0007) && (MCU_0644)

#define HARDWARE 		0x0644

#define DEV_COUNT  		8

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
	{ DEV_TYPE_OUTPUT1,		0xB1,	0xB2}, // 2 - zawor glowny
	{ DEV_TYPE_OUTPUT1,		0xC4,	0xC5}, // 3 - sekcja 1
	{ DEV_TYPE_OUTPUT1,		0xD3,	0xD2}, // 4 - sekcja 2
	{ DEV_TYPE_OUTPUT1,		0xB0,	0xD7}, // 5 - sekcja 3
	{ DEV_TYPE_OUTPUT1,		0xD6,	0xD5}, // 6 - sekcja 4
	{ DEV_TYPE_OUTPUT1,		0xC0,	0xC1}, // 7 - sekcja 5
	{ DEV_TYPE_OUTPUT1,		0xC2,	0xC3}  // 8 - sekcja 6
};
#else
extern DeviceStruct devicesTab[DEV_COUNT+1];
#endif

#endif