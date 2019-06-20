#include <inttypes.h>

#ifndef PWM_HEADER

	#define PWM_HEADER	

#if MCU_XXX8
	#define PWM_PIN_OC0A		0xD6			
	#define PWM_PIN_OC0B		0xD5
#endif

#if MCU_XXX4
	#define PWM_PIN_OC0A		0xB3
	#define PWM_PIN_OC0B		0xB4
#endif

	#ifndef PWM_BODY
	
		extern void pwmInit();
		extern void pwmSet(uint8_t channel,uint8_t value);
		extern uint8_t pwmGet(uint8_t device);

	#endif

#endif
