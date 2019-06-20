#define VOLT_BODY


#include "devices.h"
#include "debug.h"
#include "volt.h"
#include "timer.h"
#include "pin.h"
#include "event.h"
#include "eeprom.h"
#include "mess.h"
#include "utils.h"
#include "address.h"
#include "decoder.h"
#include <inttypes.h>
#include <avr/io.h>
#include <avr/interrupt.h>


/*  !!!!!! UWAGA, na razie obs³uguje jedno urz¹dzenie na hardware.
    Konfiguracja urzadzenia
	0 DEVICE(1) urzadzenie przypisane do konfiguracji
	1 VLH(1) - Wartoœæ progowa, , Low to Hight
	2 TLH(1) - Konfiguracja czasu w sekundach, Low to Hight
	3 ALH(2) – Address, Low to Hight
	5 CLH(1) – Command, Low to Hight
	6 P1LH(1) – Params, Low to Hight
	7 P2LH(1) – Params, Low to Hight
	8 VHL(1) - Wartoœæ progowa, Hight to Low
	9 THL(1) - Konfiguracja czasu w sekundach, Hight to Low
	A AHL(2) – Address, Hight to Low
	C CLH(1) – Command, Low to Hight
	D P1HL(1) – Params, Hight to Low
	E P2HL(1) – Params, Hight to Low
	F 0x00
*/


#ifdef VOLT_MODULE

#define VOLT_STATUS_LOW			0x01
#define VOLT_STATUS_HIGH		0x08


/* Konfiguracja voltage
			PARAM1	PARAM2
VOLTAGE		IN		STATUS
*/

volatile uint8_t voltDevice = 0;

	
void voltInit(void) {	
	// Wlaczenie i ustawienie przetwornika
	ADMUX |= _BV(REFS0) | _BV(ADLAR);
	ADCSRA |= _BV(ADEN) | _BV(ADSC) | _BV(ADIE) | _BV(ADPS0) | _BV(ADPS1) |  _BV(ADPS2);
	// Wyszukanie urzadzenia i ustawienie 
	for(uint8_t i = 1; i<=DEV_COUNT; ++i) {
		if(devicesTab[i].type == DEV_TYPE_VOLT) {
			voltDevice = i;
			devicesTab[i].param2 = VOLT_STATUS_LOW;
			ADMUX |= devicesTab[i].param1 & 0x0F;
			timerStart(TIMER_VOLT);
			break;
		}
	}		
}


/* Zapisanie wyniku i w³¹czenie obs³ugi */
ISR(ADC_vect) {			
	eventAdd(EVENT_VOLT);
}


void voltDone(uint8_t status, uint16_t address) {
	if(devicesTab[voltDevice].timer) {
		--devicesTab[voltDevice].timer;
	} else {
		devicesTab[voltDevice].timer = eepromReadByte(address + status + 1);
	}
	// Sprawdzenie czy timer osi¹gna³ zero
	if(!devicesTab[voltDevice].timer) {
		// Jest przejœcie
		devicesTab[voltDevice].param2 = status;
		messConfigSend(voltDevice,status == VOLT_STATUS_LOW ? VOLT_STATUS_HIGH + 2 : VOLT_STATUS_LOW + 2);
	}
}


/* Obsluga zmierzonego napiêcia */
void voltEvent(void) {
	// Sprawdzenie pomiaru
	if(voltDevice == 0) {
		return;	
	}	
	DEBUG_SNN("VE dev,value:",voltDevice, devicesTab[voltDevice].value);
	devicesTab[voltDevice].value = ADCH;
	// Sprawdzenie urzadzenia
	uint16_t address = devicesTab[voltDevice].config << 4;
	if(eepromReadByte(address) != voltDevice) {
		return;
	}
	// Pobranie progu
	uint8_t value = devicesTab[voltDevice].value;
	uint8_t status = devicesTab[voltDevice].param2;
	uint8_t threshold = eepromReadByte(address + status);
	// Sprawdzenie statusów
	if((status == VOLT_STATUS_LOW) && (value>= threshold)) {
		// Niski i przekroczono próg
		voltDone(VOLT_STATUS_HIGH,address);		
	} else if((status == VOLT_STATUS_HIGH) && (value <= threshold)) {
		// Wysoki i przekroczono próg
		voltDone(VOLT_STATUS_LOW,address);
	} else {
		devicesTab[voltDevice].timer = 0;
	}
}




#endif