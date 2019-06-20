#include <inttypes.h>
#include <avr/io.h>

#ifndef BOOT_HEADER

	#define BOOT_HEADER	
	
	#define BOOT_STATUS_NORMAL				'N' 
	#define BOOT_STATUS_BOOT				'B' 
	#define BOOT_STATUS_EMPTY				'E' 
	
	#define BOOT_OK_RESPONSE				0x10
	#define BOOT_FIND_REQUEST				0x11
	#define BOOT_BEGIN_REQUEST				0x12	
	#define BOOT_SAVE_REQUEST				0x13
	#define BOOT_END_REQUEST				0x14
	#define BOOT_GO_REQUEST					0x15
	#define BOOT_ERROR_RESPONSE				0x1F
	
// Ustawienia dla Atmega88
#if MCU_0088	
	#define BOOT_PAGE_SIZE					64
	#define BOOT_PROGRAM_SIZE				0x1C00
	#define BOOT_SERIAL_ADDRESS				0x1C04	
	#define BOOT_HARDWARE					0x0088
#endif

// Ustawienia dla Atmega168
#if MCU_0168
	#define BOOT_PAGE_SIZE					128
	#define BOOT_PROGRAM_SIZE				0x3C00
	#define BOOT_SERIAL_ADDRESS				0x3C04
	#define BOOT_HARDWARE					0x0168
#endif

// Ustawienia dla Atmega164
#if MCU_0164
	#define BOOT_PAGE_SIZE					128
	#define BOOT_PROGRAM_SIZE				0x3C00
	#define BOOT_SERIAL_ADDRESS				0x3C04	
	#define BOOT_HARDWARE					0x0164
#endif

// Ustawienia dla Atmega328
#if MCU_0328
	#define BOOT_PAGE_SIZE					128
	#define BOOT_PROGRAM_SIZE				0x7C00
	#define BOOT_SERIAL_ADDRESS				0x7C04
	#define BOOT_HARDWARE					0x0328
#endif

// Ustawienia dla Atmega324
#if MCU_0324
	#define BOOT_PAGE_SIZE					128
	#define BOOT_PROGRAM_SIZE				0x7C00
	#define BOOT_SERIAL_ADDRESS				0x7C04
	#define BOOT_HARDWARE					0x0324	
#endif

// Ustawienia dla Atmega644
#if MCU_0644
	#define BOOT_PAGE_SIZE					256
	#define BOOT_PROGRAM_SIZE				0xFC00
	#define BOOT_SERIAL_ADDRESS				0xFC04	
	#define BOOT_HARDWARE					0x0644
#endif
	
	#if DEBUG_ADDRESS == 1
		#define BOOT_GET_SERIAL_PART(pa) 		'1'
	#else
		#define BOOT_GET_SERIAL_PART(pa) 		pgm_read_byte(BOOT_SERIAL_ADDRESS + pa)
	#endif

#endif