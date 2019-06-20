#include <inttypes.h>

#ifndef CONN_HEADER

	#define CONN_HEADER	

	#define CONN_OK						0
	#define CONN_NOT_FOUND				-1	

	#define CONN_MAX_CONN 				4	// Maksymalna iloœæ pol¹czeñ
	#define CONN_MAX_WAIT_SEND			5	// Poczekaj 5 przerwan czyli ok 1s
	#define CONN_REPEAT_SEND			30	// Wysy³aj zawsze 30 razy	
	#define CONN_WAIT_TO_FINISH			30  // Czekaj na zakonczenie 30 * 200 ms = 6s
	#define CONN_WAIT_TO_REPEAT			5   // Czekaj na powtórzenie 5 * 200 ms = 1s
	#define CONN_COUNT_TO_REPEAT		5   // Iloœæ razy powtórzen wyslania	
	
	#define CONN_TYPE_EMPTY				0
	#define CONN_TYPE_SEND				1
	#define CONN_TYPE_RECEIVE			2			

	#ifndef CONN_BODY
	

		/* Ponowne wys³anie komunikatu
		 */
		extern void connEvent(void);
		
		
		/* Odebranie wiadomosci i ustalenie polaczenia
			param:				
				channel		numer kana³u			
		 */
		extern void connReceive();
		
		
		/* Wyslnie wiadomosci			
		*/
		extern int8_t connCreate(uint8_t type, uint16_t address, uint8_t device, uint8_t id, uint8_t com, uint8_t param1, uint8_t param2);
		
		
  	#endif

#endif
