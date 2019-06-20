#define SYSTEM_BODY

#include "system.h"
#include "debug.h"
#include "mess.h"
#include "address.h"
#include "decoder.h"
#include "devices.h"
#include "utils.h"
#include "eeprom.h"
#include "boot.h"
#include "channel1.h"
#include "watchdog.h"
#include <stdlib.h>
#include <avr/pgmspace.h>
#include <avr/io.h>
#include <avr/wdt.h>
#include <util/delay.h>

/*  Urz¹dzenie systemowe
	Obs³ugiwane comendy
	10 - Get makro 
	11 - Set makro
	12 - ++ makro

	Konfiguracja makro
	0 DEVICE(1) urzadzenie przypisane do konfiguracji
	1 MAKRO(2)
	3 DEV(1) - DEVICE - 1
	4 COM(1) - COMMAND - 1
	5 PAR1(1) - PARAM1 - 1
	6 PAR2(1) – PARAM1 - 1
	7 DEV(1) - DEVICE - 2
	8 COM(1) - COMMAND - 2
	9 PAR1(1) - PARAM1 - 2
	A PAR2(1) – PARAM1 - 2
	B DEV(1) - DEVICE - 3
	C COM(1) - COMMAND - 3
	D PAR1(1) - PARAM1 - 3
	E PAR2(1) – PARAM1 - 3
	F 0x00
*/

#ifdef SYSTEM_MODULE


const char VERSION[20] = __DATE__ " " __TIME__;


/* Inicjuje konfiguracje urzadzen
*/
uint8_t systemExecuteMakro(uint16_t makro) {
	uint8_t found = 0;
	// Przeszukaj wszystkie strony
	for(uint8_t page = devicesTab[0].param1; page <= devicesTab[0].param2; ++page) {	
		uint16_t pageAddress = page << 4;
		uint8_t pageDevice = eepromReadByte(pageAddress);
		uint16_t pageMakro = eepromReadWord(pageAddress+1);
		// Czy to jest urzadzenie systemowe i zgadza siê makro
		if((pageDevice == SYSTEM_DEVICE_NUMBER) && (pageMakro == makro)) {
			found = 1;		
			// Przejdz po ustawieniach urzadzeñ
			for(int  i = 0, deviceAddress = pageAddress + 0x03 ; i < 3; ++i, deviceAddress += 0x04) {
				uint8_t ndd = eepromReadByte(deviceAddress);
				if(ndd != EPPROM_EMPTY_CELL) {
					//messSystemSend(eepromReadByte(deviceAddress +1),ndd, eepromReadWord(deviceAddress+2));					
					decoderRequestReceive(ndd, eepromReadByte(deviceAddress+1), eepromReadByte(deviceAddress+2), eepromReadByte(deviceAddress+3));
				}
			}
		}
	}
	return found;
}

/*----------------------------------------------------------------------------------------------------------------------*/
/* Obs³uguje przychodz¹ce requesty do systemu
 */

void systemRequestReceive( uint8_t com, uint8_t param1, uint8_t param2) {		
	switch(com) {	
		case DECODER_REQUEST_BOOT_MODE:
			eepromWriteByte(EPPROM_HARDWARE_STATUS,BOOT_STATUS_BOOT);
			wdtRestart();
		case DECODER_REQUEST_SYSTEM_RESTART:
			wdtRestart();
		case DECODER_WATCHDOG_RESET:
			wdtReset();
			break;			
		case DECODER_REQUEST_NETWORK_ALLOCATE:		
			eepromWriteByte(EPPROM_HIGH_ADDRESS, param1);
			eepromWriteByte(EPPROM_LOW_ADDRESS, param2);
			break;									
		case DECODER_REQUEST_EEPROM_SET: {
			uint8_t page = param1;			
			uint16_t address = page << 4;
			uint8_t count = (MESS_CHECK() - 1); 
			for(uint8_t i = 0; i < count; ++i) {
				eepromWriteByte(address+i, MESS_GET_BYTE(i+1));
			}
			break;
		}
		case DECODER_REQUEST_CONFIG_SET:
			if(eepromReadByte(param2 << 4) == param1) {
				devicesTab[param1].config = param2;
			}	
			break;		
		case DECODER_CONFIG_SEND:
			_delay_ms(5000);
			break;
		case DECODER_REQUEST_COMMAND_11: {
			// Rozkaz ustawienia makra
			uint16_t makro = param1 << 8 | param2;
			if(devicesTab[0].value != makro) {
				// Nowe makro
				if(systemExecuteMakro(makro)) {
					devicesTab[0].value = makro;
				}
			}		
			break;
		}			
		case DECODER_REQUEST_COMMAND_12: {
			// Rozkaz zwiekszenia makra
			uint16_t makro = param1 << 8 | param2;
			if((makro == (devicesTab[0].value & 0xFFF0)) && systemExecuteMakro(devicesTab[0].value + 1)) {
				// Wykonano makro
				++devicesTab[0].value;
			} else {
				// Ustawienie makra na poczatkowe 0
				if(systemExecuteMakro(makro)) {
					devicesTab[0].value = makro;
				}
			}
			break;
		}
	}
}


/* Wys³anie wiadomoœci Response z numerem seryjnym, SOFTWARE i HARDWARE
 */
void systemResponseSend(uint8_t id, uint16_t add, uint8_t nsd,  uint8_t request, uint8_t param1, uint8_t param2)  {
	switch(request) {
		case DECODER_REQUEST_NETWORK_FIND:			
			// Poczekaj jakiœ czas
			for(uint16_t i = (rand() % 50)*100; i >0; --i) {
				_delay_ms(1);
			}
		case DECODER_REQUEST_NETWORK_ALLOCATE: {	
			messSerialSend(id, DECODER_RESPONSE_OK);										
			messPutWord(addressGet());							// ADDRESS		
			messPutWord(SOFTWARE);								// SOFTWARE	
			messPutWord(HARDWARE);								// HARDWARE	
			for(uint8_t i = 0; i < 20; ++ i) {					// VERSION
				MESS_PUT_BYTE(VERSION[i]);
			}
			messSend();		
			break;
		}
		case DECODER_REQUEST_EEPROM_GET:
			messSerialSend(id, DECODER_RESPONSE_OK);
			MESS_PUT_BYTE(param1);
			uint16_t address = param1 << 4;
			for(uint8_t i = 0; i < 16; ++ i) {
				MESS_PUT_BYTE(eepromReadByte(address+i));
			}										
			messSend();		
			break;
		case DECODER_REQUEST_EEPROM_SET:
		case DECODER_REQUEST_EEPROM_STATUS:
			messSerialSend(id, DECODER_RESPONSE_OK);
			MESS_PUT_BYTE(devicesTab[0].param1);
			for(uint8_t page = devicesTab[0].param1; page <= devicesTab[0].param2; ++ page) {
				MESS_PUT_BYTE(eepromReadByte(page << 4));				
			}
			messSend();
			break;
		case DECODER_REQUEST_CONFIG_SET:
			messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,param1,devicesTab[param1].config);
			break;
		case DECODER_CONFIG_SEND:
			messConfigSend(param1, param2);
			break;
		case DECODER_REQUEST_COMMAND_10:
		case DECODER_REQUEST_COMMAND_11:
		case DECODER_REQUEST_COMMAND_12:
			messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,devicesTab[0].value >> 8,devicesTab[0].value & 0xFF);
			break;
	}	
}


#endif


