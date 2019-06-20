#define OWIRE_BODY

#include "devices.h"
#include "debug.h"
#include "owire.h"
#include "pin.h"
#include <inttypes.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>


#ifdef OWIRE_MODULE


/** Inicjacja magistrali
*/
void owireInit(uint8_t device) {		
	uint8_t portpin = devicesTab[device].param1;
	pinInit(portpin,PIN_INPUT);
	pinSet(portpin,1);		
}


/** Reset magistrali
*/
uint8_t owireReset(uint8_t device){	
	uint8_t portpin = devicesTab[device].param1;
	uint8_t result = OWIRE_OK;
	cli();
	pinInit(portpin,PIN_OUTPUT);		//PIN_OUTPUT1(device);
	pinSet(portpin,0);					//PIN_RESET1(device);
   	_delay_us(500);       
	pinSet(portpin,1);					//PIN_SET1(device);
   	pinInit(portpin,PIN_INPUT);			//PIN_INPUT1(device);
   	// czekaj na odpowiedz
   	_delay_us(80);
   	if(pinGet(portpin)){				//PIN_IS_SET1(device)
    	result = OWIRE_ERR;
   	}
	_delay_us(400); 
	sei(); 
	return result;
} 


/** Wys³anie bajtu
*/
void owireWrite (uint8_t device, uint8_t data){  
	uint8_t portpin = devicesTab[device].param1;   
	cli();   	
	pinInit(portpin,PIN_OUTPUT);		//PIN_OUTPUT1(device);
   	for (uint8_t i=0; i<8; i++){ 	
		pinSet(portpin,0);				//PIN_RESET1(device); 
      	if(data & 0x01){ 						       
         	_delay_us(2);				// 2
			pinSet(portpin,1);			//PIN_SET1(device);
         	_delay_us(70);				// 70
      	}else{						
         	_delay_us(70);				// 70
			pinSet(portpin,1);			//PIN_SET1(device);
			_delay_us(2);				// 2
      	} 		
      	data>>=1; 
   	} 
	pinInit(portpin,PIN_INPUT);			//PIN_INPUT1(device);
	sei();
} 


/** Odczytanie bajtu 
*/
uint8_t owireRead (uint8_t device){ 
	uint8_t portpin = devicesTab[device].param1;
	cli();
   	uint8_t data=0; 
   	for (uint8_t i=0; i<8; i++){ 
      	data>>=1;		 
      	pinInit(portpin,PIN_OUTPUT);	//PIN_OUTPUT1(device);
		pinSet(portpin,0);				//PIN_RESET1(device);
      	_delay_us(1);
		pinSet(portpin,1);				//PIN_SET1(device);
      	pinInit(portpin,PIN_INPUT);		//PIN_INPUT1(device);
      	_delay_us(1); 
      	if(pinGet(portpin)){			//PIN_IS_SET1(device)
         	data|=0x80; 
      	} 
      	_delay_us(50); 
   	} 
	sei();
   	return data;
}

#endif
