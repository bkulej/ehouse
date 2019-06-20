#include <inttypes.h>

#ifndef LCDT_HEADER

	#define LCDT_HEADER	

	#ifndef LCDT_BODY		


		/* inicjuje lcd tekstowe   			
		*/
		void lcdtInit();


		/* wyswietla tekst na lcd
	   		@param 
  				text - tekst na wyswietlacz
 		*/
		void lcdtText(char * text);


		/* wyswietla liczbe na lcd
	   		@param 
  				in - tekst na wyswietlacz
 		*/
		void lcdtInt(int32_t in, uint8_t length, uint8_t comma);


		/* Przenosi kursor do podanego miejsca
		*/
		void lcdtXY(uint8_t x, uint8_t y);

		/* inicjuje lcd tekstowe   			
		*/
		void lcdtClear();

	#endif

#endif
