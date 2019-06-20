#define EEPROM_BODY

#include "devices.h"
#include "debug.h"
#include "eeprom.h"
#include <avr/eeprom.h> 

#ifdef EEPROM_MODULE

/* Zapisuje bajt do pamieci eeprom
 */
void eepromWriteByte(uint16_t address, uint8_t data) {
	// ustaw addres
	EEAR = address;
	// ustaw bajt do zapisu
	EEDR = data;
	// umo¿liwienie odczytu eeproma
	EECR |= _BV(EEMPE);
	// odczyt bajtu z pamiêci
	EECR |= _BV(EEPE);
	// czekaj na zakoñczenie zapisywania
	while(EECR & (_BV(EEPE)) );
}


/* Zwraca bajt zapisany pod podanym addresem z pamieci eeprom
 */
uint8_t eepromReadByte(uint16_t address) {
	// ustaw addres
	EEAR = address;
	// umo¿liw zapis
	EECR |= _BV(EERE);
	// zwrócenie odczytanego bajtu
	return EEDR;
}


/* Zwraca bajt zapisany pod podanym addresem z pamieci eeprom
 */
uint16_t eepromReadWord(uint16_t address) {
	// ustaw addres
	EEAR = address;
	// umo¿liw zapis
	EECR |= _BV(EERE);
	// zwrócenie odczytanego bajtu
	uint16_t data = EEDR << 8;
	// ustaw addres
	EEAR = address + 1;
	// umo¿liw zapis
	EECR |= _BV(EERE);
	// zwrócenie odczytanego bajtu
	return data | EEDR;
}

#endif

