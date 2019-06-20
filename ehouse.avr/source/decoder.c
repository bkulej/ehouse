#define DECODER_BODY

#include "decoder.h"
#include "debug.h"
#include "decoder.h"
#include "mess.h"
#include "address.h"
#include "eeprom.h"
#include "utils.h"
#include "system.h"
#include "channel1.h"
#include "devices.h"
#include "pin.h"
#include "main.h"
#include "output.h"
#include "input.h"
#include "therm.h"
#include "pwm.h"
#include <util/delay.h>


#ifdef DECODER_MODULE


uint8_t decoderIsRequest(uint8_t com) {
	switch(com) {
		case DECODER_RESPONSE_OK:
		case DECODER_RESPONSE_ERROR:
			return 0;
		default :
			return 1;
	}	
}


/* Obs³uguje przychodz¹ce Requesty
 */
void decoderRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {	
	DEBUG_SNNNN("DEC ndd,com,par1,par2:",ndd,com,param1,param2);
		
	// DEVICE_SYSTEM
	if(devicesTab[ndd].type == DEV_TYPE_SYSTEM) { 
		systemRequestReceive(com,param1,param2);
		
#ifdef DEVICE_MAIN
	} else if (devicesTab[ndd].type >= DEV_TYPE_MAIN_BEGIN) {	
		mainRequestReceive(ndd,com,param1,param2);
#endif	

#ifdef OUTPUT_MODULE
	} else if (devicesTab[ndd].type == DEV_TYPE_OUTPUT1) {
		uint16_t delay = param1 << 8 | param2;
		switch(com) {
			case DECODER_REQUEST_COMMAND_11: {				// OFF
				outputSet(ndd,OUTPUT_OFF,delay);
				break;
			}
			case DECODER_REQUEST_COMMAND_12: {				// OPEN
				outputSet(ndd,OUTPUT_OPEN,delay);
				break;
			}
			case DECODER_REQUEST_COMMAND_13: {				// CLOSE
				outputSet(ndd,OUTPUT_CLOSE,delay);
				break;
			}
			case DECODER_REQUEST_COMMAND_14: {				// OPEN_OR_OFF
				outputSet(ndd,OUTPUT_OPEN_OR_OFF,delay);
				break;
			}
			case DECODER_REQUEST_COMMAND_15: {				// CLOSE_OR_OFF
				outputSet(ndd,OUTPUT_CLOSE_OR_OFF,delay);
				break;
			}
			case DECODER_REQUEST_COMMAND_16: {				// HOLD
				switch(outputGet(ndd)) {
					case OUTPUT_OPEN : {
						outputSet(ndd,OUTPUT_OPEN,delay);
						break;
					}
					case OUTPUT_CLOSE : {
						outputSet(ndd,OUTPUT_CLOSE,delay);
						break;
					}
				}
				break;
			}
		}
#endif

#ifdef PWM_MODULE
	} else if (devicesTab[ndd].type == DEV_TYPE_PWM) {
		if(com ==  DECODER_REQUEST_COMMAND_11) {
			pwmSet(ndd,param1);
		}
#endif
	
	} 
	
#ifdef DEVICE_MAIN
	if (com ==  DECODER_SYSTEM_ALARM) {
		mainRequestReceive(ndd,com,param1,param2);
	}
#endif

}


/* Obs³uguje wysylane Response
 */
void decoderResponseSend(uint16_t add, uint8_t nsd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {	
	// DEVICE_SYSTEM
	if(devicesTab[nsd].type == DEV_TYPE_SYSTEM) { 
		systemResponseSend(id,add,nsd,com,param1,param2);	
					
#ifdef DEVICE_MAIN
	} else if (devicesTab[nsd].type >= DEV_TYPE_MAIN_BEGIN) {
		mainResponseSend(add,nsd,id,com,param1,param2);			
#endif

#ifdef OUTPUT_MODULE
	} else if(devicesTab[nsd].type == DEV_TYPE_OUTPUT1) {
		messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,outputGet(nsd),0x00);
#endif

#ifdef THERM_MODULE
	} else if(devicesTab[nsd].type == DEV_TYPE_THERM) {
		uint16_t value = thermRead(nsd);
		messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,value>>8,value & 0xFF);
#endif

#ifdef INPUT_MODULE
	} else if (devicesTab[nsd].type == DEV_TYPE_INPUT) {
		uint8_t value = com == DECODER_REQUEST_COMMAND_11 ? devicesTab[nsd].config : pinGet(devicesTab[nsd].param1);
		messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,value,0x00);
#endif

#ifdef PWM_MODULE
	} else if (devicesTab[nsd].type == DEV_TYPE_PWM) {
		messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,pwmGet(nsd),0x00);
#endif

#ifdef VOLT_MODULE
	} else if (devicesTab[nsd].type == DEV_TYPE_VOLT) {
		uint8_t value = com == DECODER_REQUEST_COMMAND_11 ? devicesTab[nsd].config : devicesTab[nsd].value;
		messAddressSend(MESS_MODE_END,add,nsd,id,DECODER_RESPONSE_OK,value,0x00);
#endif
			
	}	
}


/* Obs³uguje przychodz¹ce Requesty
 */
void decoderResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {		
#ifdef DEVICE_MAIN
	if(devicesTab[ndd].type >= DEV_TYPE_MAIN_BEGIN) {
		mainResponseReceive(asd, ndd, id, com, param1, param2);		
	}	
#endif
}


#endif

