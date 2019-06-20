#ifndef MAIN_HEADER

	#define MAIN_HEADER	

	#ifdef MAIN_BODY

		/** Inicjacja podprogramu
		*/
		extern void mainInit();
		
		/** Uruchomienie podprogramu
		*/
		extern void mainThread();
		
		/* Kontynuacja innych watkow
		*/
		extern void mainContinue();
				
	#else
	
		/* Kontynuacja innych watkow
		*/
		extern void mainContinue();
	
		/** Obs³uga przychodzacego requestu
		*/
		extern void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2);
		
		/** Obs³uga wys³ania response
		*/
		extern void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2);
		
		/** Obs³uga przychodzacego response
		*/
		extern void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2);

	#endif	

#endif
