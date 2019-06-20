#include <inttypes.h>

#ifndef DECODER_HEADER

	#define DECODER_HEADER
	
	#define DECODER_RESULT_NULL							0x00
		
	#define DECODER_RESPONSE_OK							0x00
	#define DECODER_REQUEST_SYSTEM_RESTART 				0x01
	#define DECODER_SYSTEM_INFO	 						0x02
	#define DECODER_REQUEST_NETWORK_FIND 				0x03
	#define DECODER_REQUEST_NETWORK_ALLOCATE 			0x04
	#define DECODER_REQUEST_EEPROM_GET					0x05
	#define DECODER_REQUEST_EEPROM_SET					0x06
	#define DECODER_REQUEST_EEPROM_STATUS				0x07
	#define DECODER_REQUEST_CONFIG_SET					0x08
	#define DECODER_REQUEST_BOOT_MODE					0x09
	#define DECODER_SYSTEM_ALARM						0x0A
	#define DECODER_WATCHDOG_RESET						0x0B
	#define DECODER_CONFIG_SEND							0x0C
	#define DECODER_RESPONSE_ERROR						0x0F
	
	#define DECODER_REQUEST_COMMAND_10					0x10	
	#define DECODER_REQUEST_COMMAND_11					0x11	
	#define DECODER_REQUEST_COMMAND_12					0x12
	#define DECODER_REQUEST_COMMAND_13					0x13
	#define DECODER_REQUEST_COMMAND_14					0x14
	#define DECODER_REQUEST_COMMAND_15					0x15
	#define DECODER_REQUEST_COMMAND_16					0x16
	#define DECODER_REQUEST_COMMAND_17					0x17
	#define DECODER_REQUEST_COMMAND_18					0x18
	#define DECODER_REQUEST_COMMAND_19					0x19
	#define DECODER_REQUEST_COMMAND_1A					0x1A
	#define DECODER_REQUEST_COMMAND_1B					0x1B
	#define DECODER_REQUEST_COMMAND_1C					0x1C
	#define DECODER_REQUEST_COMMAND_1D					0x1D
	#define DECODER_REQUEST_COMMAND_1E					0x1E
	#define DECODER_REQUEST_COMMAND_1F					0x1F
	
	#define DECODER_DEBUG_F0							0xF0
	#define DECODER_DEBUG_F1							0xF1
	#define DECODER_DEBUG_F2							0xF2
	#define DECODER_DEBUG_F3							0xF3
	#define DECODER_DEBUG_F4							0xF4
	#define DECODER_DEBUG_F5							0xF5
	#define DECODER_DEBUG_F6							0xF6
	#define DECODER_DEBUG_F7							0xF7
	#define DECODER_DEBUG_F8							0xF8
	#define DECODER_DEBUG_F9							0xF9
	#define DECODER_DEBUG_FA							0xFA
	#define DECODER_DEBUG_FB							0xFB
	#define DECODER_DEBUG_FC							0xFC
	#define DECODER_DEBUG_FD							0xFD
	#define DECODER_DEBUG_FE							0xFE
	#define DECODER_DEBUG_FF							0xFF
	
	#define DECODER_ALARM_BOLIER_TEMP_HIGH				0x3201							
	

	#ifndef DECODER_BODY
	
		extern uint8_t decoderIsRequest(uint8_t com);

		extern void decoderRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2);
		
		extern void decoderResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2);

		extern  void decoderResponseSend(uint16_t add, uint8_t nsd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2);
		
		
  	#endif

#endif
