#include <inttypes.h>

#ifndef MESS_HEADER

	#define MESS_HEADER
	
	#define	MESS_CHANNEL0 				0
	#define	MESS_CHANNEL1 				1
	
	#define MESS_MODE_RESPONSE			0x00
	#define MESS_MODE_REQUEST			0x01
	#define MESS_MODE_REPEAT			0x02
	#define MESS_MODE_END				0x04
	
	
	#define MESS_TYPE_SERIAL			0x00
	#define MESS_TYPE_ADDRESS			0x01
	#define MESS_TYPE_BOOT				0x02
	
	#define MESS_CHECK()				(CHANNEL1_DATA_COUNT - 7)
	#define MESS_GET_TYPE()				(channel1Get(0) & 0x0F)
	#define MESS_GET_ADD()				messGetWord(1)
	#define MESS_GET_ASD()				messGetWord(3)
	#define MESS_GET_ID()				channel1Get(5)
	#define MESS_GET_COM()				channel1Get(6)
	#define MESS_GET_NDD()				messGetNdd()
	#define MESS_GET_SERIAL(i)			channel1Get(i+1)
	#define MESS_GET_BYTE(i)			channel1Get(i+7)
	#define MESS_PUT_BYTE(data)			channel1Put(data)
	

	#ifndef MESS_BODY

		/*	Generowanie ID
		*/
		extern uint8_t messGetId(void);
		
		extern void messAddressSend(uint8_t mode, uint16_t add, uint8_t device, uint8_t id, uint8_t com, uint8_t param1, uint8_t param2);
		
		extern void messSerialSend(uint8_t id, uint8_t com);
		
		extern void messSystemSend(uint8_t com, uint8_t device, uint16_t param);
		
		
		/* Wstawia s³owo (2 bajty) do wiadomoœci   		
		 */
		extern void messPutWord(uint16_t data);
				

		/* WYkonuje wiadomosc jako event   		
		*/
		extern void messSend(void);
		
		
		/* Funkcja zwraca iloœæ danych w odebranym komunikacie
		@param
			chanel - numer kana³u
		@return
			0x00	MESS_TYPE_SERIAL	
			0x01	MESS_TYPE_ADDRESS					
		*/ 
		extern uint8_t messGetType();	

		
		/* Funkcja odbiera numer zrodlowego urzadzenia
		@return
			NDD
		*/
		extern uint8_t messGetNdd();
		
		
		/* Funkcja odbiera bajt (4 znaki) z kana³u
		@param
			index  - index DATA
		@return
			DATA[index]
		*/
		extern uint16_t messGetWord(int8_t index);	
		
		
		/* Funkcja wysyla rozkaz z pamieci configa w pamieci eeprom
		*/
		extern void messConfigSend(uint8_t device, uint8_t offset);
				

  	#endif

#endif
