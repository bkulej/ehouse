#include <inttypes.h>

#ifndef CHANNEL1_HEADER

	#define CHANNEL1_HEADER
  
	#define CHANNEL1_OK 			0	// Operacja poprawna
	
	#define CHANNEL1_NO_CLEAR_TX	0
	#define CHANNEL1_CLEAR_TX		1
	
	#define CHANNEL1_RX_NO_COPY		0
	#define CHANNEL1_RX_COPY		1
	
	
	
	#define CHANNEL1_DATA_COUNT		channel1RxCount


	#ifndef CHANNEL1_BODY

	    /* Inicjuje protokó³ 
		*/
	    extern void channel1Init(void);


		/* Rozpoczyna komunikat		   	
		   	param: 
		    	header - nag³ówek wiadomosci
		*/
		extern void channel1New(uint8_t header, uint8_t rxCopy);


		/* Wstawia 8 bitów do komunikatu 
		   	param:
		     	d01 - kolejne bajty komunikatu (tylko 7bitów uwzgledniane)				
		*/
		extern void channel1Put(uint8_t d01);


	    /* Zakonczenie	   		   	
		*/
		extern void channel1End();
		
		
		/* Wyslanie komunikatu
		*/
		extern void channel1Send(void);
		
		
		/* Czyœci bufor nadawczy
		*/
		extern void channel1TxClear(void);


		/* Definiuje ile bajtów jest w buforze odbiornika			
		*/
		extern volatile uint8_t channel1RxCount;


		/* Pobiera znak z bufora 
			param:
		     	i - numer znaku w buforze
			return:				
				Znak z bufora
		*/
		extern uint8_t channel1Get(uint8_t i);


		/* Czyœci bufor odbiorczy
		*/
		extern void channel1RxClear(void);
		
				
		
		

  	#endif

#endif
