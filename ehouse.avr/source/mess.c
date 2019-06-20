#define MESS_BODY

#include "devices.h"
#include "debug.h"
#include "mess.h"
#include "channel1.h"
#include "decoder.h"
#include "conn.h"
#include "utils.h"
#include "eeprom.h"
#include "address.h"
#include "event.h"
#include "boot.h"
#include <avr/pgmspace.h>

#ifdef MESS_MODULE

#define MESS_MAX_ID		0xFF

volatile uint8_t messId = 0;

/*	Generowanie ID
*/
uint8_t messGetId(void) {
	if(messId >= MESS_MAX_ID) {
		messId = 0;
	}
	++messId;
	return messId;
}

/* Wstawienie danych do komunikatu 
  */
void messPutWord(uint16_t data) {
	channel1Put(data >> 8);
	channel1Put(data);
}

/* Rozpoczêcie nowego komunikatu
 */
void messAddressSend(uint8_t mode, uint16_t add, uint8_t device, uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
	uint8_t rxCopy;
	uint16_t address = addressGet();
	if (addressIsBroadcast(add)) {
		// Do wszystkich
		if(mode & MESS_MODE_REPEAT) {
			rxCopy = CHANNEL1_RX_NO_COPY;
		} else {
			rxCopy = CHANNEL1_RX_COPY;
		}
		if(mode & MESS_MODE_REQUEST) {
			connCreate(CONN_TYPE_SEND, add, device, id, com, param1, param2);
		}	
	} else if (address == (add & 0xFFF0)) {
		// Do mnie
		rxCopy = CHANNEL1_RX_COPY;
	} else {
		// Do innych
		rxCopy = CHANNEL1_RX_NO_COPY;
		if(mode & MESS_MODE_REQUEST) {
			connCreate(CONN_TYPE_SEND, add, device, id, com, param1, param2);
		}
	}
	channel1New(MESS_TYPE_ADDRESS, rxCopy);
	messPutWord(add);
	messPutWord(address | device);
	channel1Put(id);
	channel1Put(com);
	if(mode & MESS_MODE_END) {
		channel1Put(param1);
		channel1Put(param2);
		channel1End();
	}	
}


/* Rozpoczêcie nowego komunikatu
 */
void messSystemSend(uint8_t com, uint8_t device, uint16_t param) {
	channel1New(MESS_TYPE_ADDRESS, 0);
	messPutWord(com == DECODER_SYSTEM_ALARM ? ADDRESS_BROADCAST : ADDRESS_COMPUTER);
	messPutWord(addressGet() | device);
	channel1Put(messGetId());
	channel1Put(com);
	messPutWord(param);
	channel1End();	
}


/* Rozpoczêcie nowego komunikatu
 */
void messSerialSend(uint8_t id, uint8_t com) {		
	channel1New(MESS_TYPE_SERIAL, CHANNEL1_RX_NO_COPY);	
 	for(uint8_t i = 0; i < 8; i= i + 2) {
	 	channel1Put(utilsCharToBits(BOOT_GET_SERIAL_PART(i),4) | utilsCharToBits(BOOT_GET_SERIAL_PART(i+1),0));		// SERIAL
 	}		
 	channel1Put(id);
	channel1Put(com);
}


/* Zakoñczenie komunikatu jako event
 */
void messSend(void) {
	channel1End();	
}


/* Pobranie numeru device
 */
uint8_t messGetNdd() {
	return channel1Get(2) & 0x0F;				
}


uint16_t messGetWord(int8_t index) {
	return (channel1Get(index) << 8) | channel1Get(index + 1);
}


void messConfigSend(uint8_t device, uint8_t offset) {
	uint16_t address = devicesTab[device].config << 4;
	if(eepromReadByte(address) != device) {
		return;
	}
	address += offset;
	uint16_t add = eepromReadWord(address);
	if(add != ADDRESS_EMPTY) {
		messAddressSend(MESS_MODE_REQUEST | MESS_MODE_END, 
			add, device, messGetId(), eepromReadByte(address+2), eepromReadByte(address+3), eepromReadByte(address+4));
	}
}


#endif
