#define LCDT_BODY

#include "devices.h"
#include "debug.h"
#include "lcdt.h"
#include "utils.h"
#include <inttypes.h>
#include <avr/io.h>
   
#ifdef LCDT_MODULE

#if MCU_XXX8	
	#define LCD_DDR			DDRC
	#define LCD_PORT 		PORTC
	#define LCD_PIN_EN 		PC4                   
	#define LCD_PIN_RS 		PC5	
#endif

#if MCU_XXX4
	#define LCD_DDR			DDRC
	#define LCD_PORT 		PORTC
	#define LCD_PIN_EN 		PC3                   
	#define LCD_PIN_RS 		PC2
#endif

void lcdtWrite(uint8_t data) { 		
	LCD_PORT |= _BV(LCD_PIN_EN);	
#if MCU_XXX8
	LCD_PORT = ((LCD_PORT & 0xF0) | (data & 0xF0) >> 4);	
#endif
#if MCU_XXX4
	LCD_PORT = ((LCD_PORT & 0x0F) | (data & 0xF0));	
#endif
	LCD_PORT &= ~_BV(LCD_PIN_EN);
	asm("nop");	
	LCD_PORT |= _BV(LCD_PIN_EN);	
#if MCU_XXX8		
	LCD_PORT = ((LCD_PORT & 0xF0) | (data & 0x0F));	
#endif
#if MCU_XXX4	
	LCD_PORT = ((LCD_PORT & 0x0F) | ((data & 0x0F) << 4));		
#endif
	LCD_PORT &= ~_BV(LCD_PIN_EN);
	asm("nop");	
	LCD_PORT |= _BV(LCD_PIN_EN);	
	utilsDelayMs(1);
}


void lcdtCommand(uint8_t data) { 
	LCD_PORT &= ~_BV(LCD_PIN_RS);
	lcdtWrite(data);	
	utilsDelayMs(10);
} 


void lcdtInit() { 	
	utilsDelayMs(200);
	// Ustawienie wyjsc
#if MCU_XXX8
	LCD_DDR |= 0x0F;
#endif
#if MCU_XXX4
	LCD_DDR |= 0xF0;	
#endif	
	LCD_DDR |= _BV(LCD_PIN_EN);
	LCD_DDR |= _BV(LCD_PIN_RS);		
	// Wyzerowanie sterowania
	LCD_PORT &= ~_BV(LCD_PIN_EN);
	LCD_PORT &= ~_BV(LCD_PIN_RS);
	utilsDelayMs(10);
	LCD_PORT |= _BV(LCD_PIN_EN);
	LCD_PORT |= _BV(LCD_PIN_RS);
	// Ustawienie wyswietlacza	
	lcdtCommand(0x30); // interfejs 4-bity, 2-linie, znak 5x7 
	lcdtCommand(0x28); // interfejs 4-bity, 2-linie, znak 5x7 
	lcdtCommand(0x28); // interfejs 4-bity, 2-linie, znak 5x7 
	lcdtCommand(0x08); // wy³¹cz LCD, kursor i miganie 
	lcdtCommand(0x01); // czyœæ LCD 
	lcdtCommand(0x06); // bez przesuwania w prawo 
	lcdtCommand(0x0C); // w³¹cz LCD, bez kursora i mrugania 
	utilsDelayMs(10);	
}
 

void lcdtText(char* text) { 	
	LCD_PORT |= _BV(LCD_PIN_RS);
	while(*text) { 
  		lcdtWrite(*text);
  		text++;
  	} 
} 

/* Konwertuje liczbe ca³kowit¹ na string. Mozna przekonwertowac float mno¿¹c
   liczbê przez 10,100,1000, .. i wstawiajac na odpowiednim miejscu przecinek
	param:
		in 		- liczba do konwersji
		out		- przekonwerotwany string
		length  - dlugoœæ ci¹gu znaków
		comma	- na którym miejscu przecinek
*/
void lcdIntToString(int32_t in, char * out, uint8_t length, uint8_t comma ) {	
	char tmpout[10];
	// Ustalenie dlugosci
	int8_t len = length;
	if(comma){
		--len;
	}
	// Obliczenie wartosci bezwzglednej
	uint8_t negative;
	int32_t tmpin;	
	if(in < 0) {
		tmpin = in * -1;
		negative = 1;
		--len;
	} else {
		tmpin = in;
		negative = 0;
	}
	// Konwersja na liczbe
	for(int8_t i = 9; i >= 0; i--) {
		tmpout[i] = (tmpin % 10) + '0';
		tmpin /= 10;
	}	
	// Przepisanie wyniku
	uint8_t pos = 0;
	uint8_t nozero = 0;
	for(uint8_t i = 0; i<=9; i++) {		
		// pominiecie przednich zer
		if((tmpout[i] != '0') || (9-i == comma)) {
			nozero = 1;
		}
		// wstawienie minusa
		if(nozero && negative) {
			out[pos++] = '-';			
			negative = 0;
		}
		// wstawienie spacji wymuszonej d³ugoœci
		if(!nozero && (len > 0) && (9-i < len)) {
			out[pos++]=' ';			
		}
		// wstawienie przecinka
		if((comma > 0) && (9-i == comma-1)) {
			out[pos++]=',';			
		}
		// przepisanie znaku
		if(nozero) {
			out[pos++] = tmpout[i];			
		}
	}
	out[pos]= 0;
}


void lcdtInt(int32_t in, uint8_t length, uint8_t comma) {
	char tmp[12];
	lcdIntToString(in,tmp, length, comma);		
	lcdtText(tmp);
}


void lcdtXY(uint8_t x, uint8_t y) { 
   lcdtCommand((x*0x40+y) | 0x80); 
}           	


void lcdtClear() {
	lcdtCommand(0x01);
}

#endif







