#ifndef DEBUG_HEADER

	#define DEBUG_HEADER	

	#ifndef DEBUG_BODY

		#if DEBUG_MODULE == 1

			/* Inicjacja debugera
			*/
			extern void debugPinsInit();
			
			/* Wys³anie bajtu
			*/
			extern void debugPinsChar(uint8_t data);

			/* Wys³anie komunikatu poprzez pin
			*/
			extern void debugPinsSendSN(char *message,uint8_t arg, uint32_t value);			
			
			extern void debugPinsSendSS(char *message,char *message1);
			
			extern void debugPinsSendSC(char *message,char data);
			
			extern void debugPinsSendN(uint8_t data);
					
		#endif

	#endif

	#if DEBUG_MODULE == 1
		#define DEBUG_INIT debugPinsInit();
		#define DEBUG_C(chr) debugPinsChar(chr);
		#define DEBUG_N(chr) debugPinsSendN(chr);
		#define DEBUG_S(str) debugPinsSendSN(str,0,0);
		#define DEBUG_SC(str,chr) debugPinsSendSC(str,chr);
		#define DEBUG_SS(str1,str2) debugPinsSendSS(str1,str2);		
		#define DEBUG_SN(str,val1) debugPinsSendSN(str,32,(uint32_t)val1);	
		#define DEBUG_SNN(str,val1,val2) debugPinsSendSN(str,16,((uint32_t)val1<<16) | (((uint32_t)val2)&0xffff));
	  	#define DEBUG_SNNNN(str,val1,val2,val3,val4) debugPinsSendSN(str,8,(((uint32_t)val1<<24)&0xff000000) | (((uint32_t)val2<<16)&0x00ff0000) | (((uint32_t)val3<<8)&0x0000ff00) | ((uint32_t)val4&0x000000ff));
		
		
    #else
		#define DEBUG_INIT ;
		#define DEBUG_C(chr) ;
		#define DEBUG_S(str) ;
		#define DEBUG_SC(str,chr);
		#define DEBUG_SS(str1,str2);
	  	#define DEBUG_SN(str,val1) ;
	  	#define DEBUG_SNN(str,val1,val2) ;
	  	#define DEBUG_SNNNN(str,val1,val2,val3,val4) ;		
	#endif

#endif
