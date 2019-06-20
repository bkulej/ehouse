#define GSM_BODY

#include "devices.h"
#include "debug.h"
#include "gsm.h"
#include "utils.h"
#include "event.h"
#include <inttypes.h>
#include <string.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/atomic.h>
#include <avr/pgmspace.h> 

#ifdef GSM_MODULE

#define GSM_DDR_PWR				DDRD
#define GSM_PORT_PWR			PORTD
#define GSM_PIN_PWR				PIND
#define GSM_BIT_PWR				PD5

#define GSM_DDR_STAT			DDRD
#define GSM_PORT_STAT			PORTD
#define GSM_PIN_STAT			PIND
#define GSM_BIT_STAT			PD6

#define GSM_UART_SPEED_UBRR 	25 					// 38461b/s dla 16MHz

#define GSM_RX_BUFFER_SIZE 		200					// Rozmiar bufora odbiorczego, mie mo¿e byæ wiêkszy ni¿ 256 ze wzglêdu na typy gsmRx...
volatile uint8_t gsmRxBuffer[GSM_RX_BUFFER_SIZE];	// Nie mo¿e byæ wiêkszy ni¿ 256 ze wzglêdu na typy gsmRx...

#define GSM_RX_PART_SIZE		30
volatile char gsmRxPart[GSM_RX_PART_SIZE];


volatile uint8_t gsmRxRead = 0;
volatile uint8_t gsmRxWrite = 0;
volatile uint8_t gsmRxCount = 0;
volatile uint8_t gsmRxPartPosition = 0;


void gsmModemChar(uint8_t data);
void gsmModemText(char *text);
uint8_t gsmModemRead(void);
void gsmEventDecode(void);
void gsmEventStatusEmpty();
void gsmEventStatusRingBegin();
void gsmEventStatusRingFinish();
void gsmEventStatusSmsRingBegin();
void gsmEventStatusSmsRingFinish();
void gsmEventStatusSmsReadBegin();
void gsmEventStatusSmsReadNext();
void gsmEventStatusSmsReadCommand();
void gsmEventStatusSmsReadFinish();


// Inicjacja modu³u
void gsmInit(void) {	
	// Ustaw zasilanie jako wyjœcie
	GSM_DDR_PWR |= _BV(GSM_BIT_PWR);
	GSM_PORT_PWR |= _BV(GSM_BIT_PWR);
	// Ustaw jako wejscie
	GSM_DDR_STAT &= ~_BV(GSM_BIT_STAT);
	GSM_PORT_STAT &= ~_BV(GSM_BIT_STAT);	
	// Czy modem jest w³aczony	
	while((GSM_PIN_STAT & _BV(GSM_BIT_STAT)) == 0) {		
		GSM_PORT_PWR &= ~_BV(GSM_BIT_PWR);
		utilsDelayMs(2000);
		GSM_PORT_PWR |= _BV(GSM_BIT_PWR);	
		utilsDelayMs(2000);
	}
	// Ustawienie szybkosci, asynchroniczna, 8bitów, 1 bit stop
	UBRR1H = (uint8_t)(GSM_UART_SPEED_UBRR>>8);	
	UBRR1L = (uint8_t) GSM_UART_SPEED_UBRR;	
	UCSR1C = _BV(UCSZ11) | _BV(UCSZ10);	
	UCSR1B = _BV(RXEN1) | _BV(TXEN1) | _BV(RXCIE1);	
	// Synchronizuj 
	gsmModemChar('A');
	utilsDelayMs(3000);
	// Odetkanie modemu
	gsmModemText("\rAT\r");
	utilsDelayMs(1000);
	// Wy³¹czenie echa	
	gsmModemText("ATE0\r");
	utilsDelayMs(1000);
	// Ustawienie tekstowej reprezentacji SMSów
	gsmModemText("AT+CMGF=1\r");
	utilsDelayMs(1000);
	// Listowanie rozmów
	gsmModemText("AT+CLCC=1\r");
	utilsDelayMs(1000);
	// Oproznij bufor
	while(gsmRxCount!=0) {
		gsmModemRead();
	}
	// Odczytaj pierwszy sms
	gsmModemText("AT+CMGR=1\r");
}


// Wyslanie danych do seriala
void gsmModemChar(uint8_t data) {
	while (!(UCSR1A & (1<<UDRE1)));	
	UDR1=data;
}


// Wys³anie ci¹gu do modemu
void gsmModemText(char *text) {
	char data;	
	while ((data=*text++)!=0){
		gsmModemChar(data);
	}
}


// Pobranie danej
uint8_t gsmModemCheck(void) {	
	return gsmRxCount >0 ? 1 : 0;
}


// Pobranie danej
uint8_t gsmModemRead(void) {	
	// Czekaj na znak
	while (gsmRxCount == 0);
	// Pobierz znak z bufora		
	uint8_t data = gsmRxBuffer[gsmRxRead];
	--gsmRxCount;	
	// Zwiêksz pozycje do odczytu
	++gsmRxRead;
	if(gsmRxRead == GSM_RX_BUFFER_SIZE) {
		gsmRxRead = 0;
	}	
	return data;
}


// Obs³uga przyjœcia wiadomosci
ISR(USART1_RX_vect) { 
	// Pobierz znak
	uint8_t data = UDR1;	
	// Je¿eli jest miejsce to wpisz
	if(gsmRxCount < GSM_RX_BUFFER_SIZE) {					
		// Wstaw znak do bufora				
		gsmRxBuffer[gsmRxWrite] = data;
		++gsmRxCount;
		// Zwiêksz pozycje do zapisu
		++gsmRxWrite;
		if(gsmRxWrite == GSM_RX_BUFFER_SIZE) {
			gsmRxWrite = 0;
		}		
		// Jezeli new line to dodaj event
		if(data == '\n') {			
			eventAdd(EVENT_GSM);
		}
	}	
}

#define GSM_SEPARATOR_EMPTY		0
#define GSM_SEPARATOR_FULL		1

// Odczyt lini z bufora
uint8_t gsmReadPart(void) {
	while(gsmModemCheck()) {
		uint8_t data = gsmModemRead();		
		switch(data) {
			case '\n':				
			case ',':				
			case ':':				
			case '(':										
			case ')':				
				gsmRxPart[gsmRxPartPosition] = '\0';
				gsmRxPartPosition = 0;
				return data;			
			case '\r':				
			case ' ':
			case '"':
				break;
			default:				
				gsmRxPart[gsmRxPartPosition] = data;
				++gsmRxPartPosition;
		}				
		if(gsmRxPartPosition >= (GSM_RX_PART_SIZE-1)) {
			gsmRxPart[gsmRxPartPosition] = '\0';
			gsmRxPartPosition = 0;
			return GSM_SEPARATOR_FULL;
		}
	}	
	return GSM_SEPARATOR_EMPTY;
}


#define GSM_RX_STATUS_EMPTY						0
#define GSM_RX_STATUS_SMS_RING_BEGIN			1
#define GSM_RX_STATUS_SMS_RING_FINISH			2
#define GSM_RX_STATUS_SMS_READ_BEGIN			3
#define GSM_RX_STATUS_SMS_READ_NEXT				4
#define GSM_RX_STATUS_SMS_READ_COMMAND			5
#define GSM_RX_STATUS_SMS_READ_FINISH			6
#define GSM_RX_STATUS_RING_BEGIN				7
#define GSM_RX_STATUS_RING_FINISH				8


// Status analizy danych z modemu
volatile struct GsmRxStatus {
	uint8_t type;
  	uint8_t position;
	uint8_t separator;
	uint8_t lastSeparator;
	char phone[13];
  	char command[17];
} gsmRxStatus;


// Ustawienie statusu
void gsmRxStatusSet(uint8_t type) {
	gsmRxStatus.type = type;
	gsmRxStatus.position = 0;
	if(type == GSM_RX_STATUS_EMPTY) {
		gsmRxStatus.phone[0] = '\0';
		gsmRxStatus.command[0] = '\0';
		gsmRxStatus.separator = '\0';
		gsmRxStatus.lastSeparator = '\0';
	}
}


// Dekodowanie danych z gsma
void gsmEvent(void) {
		
	// Pobierz czesci z bufora
	while((gsmRxStatus.separator = gsmReadPart())!= GSM_SEPARATOR_EMPTY) {
		
		// Zwiêksz pozycje
		++ gsmRxStatus.position;
				
		// Dekoduj operacje do rozpoczêcia
		switch(gsmRxStatus.type) {
			case GSM_RX_STATUS_EMPTY :
				gsmEventStatusEmpty();								
				break;
			case GSM_RX_STATUS_RING_BEGIN :
				gsmEventStatusRingBegin();
				break;		 
			case GSM_RX_STATUS_SMS_RING_BEGIN :
				gsmEventStatusSmsRingBegin();								
				break;
			case GSM_RX_STATUS_SMS_READ_BEGIN :
				gsmEventStatusSmsReadBegin();
				break;
			case GSM_RX_STATUS_SMS_READ_NEXT :
				gsmEventStatusSmsReadNext();
				break;
		}		
		// Uzupe³nij ostatni separator				
		gsmRxStatus.lastSeparator = gsmRxStatus.separator;
		
		// Dekoduj zakoñczone operacje
		switch (gsmRxStatus.type){
			case GSM_RX_STATUS_RING_FINISH:
				gsmEventStatusRingFinish();	
				break;
			case GSM_RX_STATUS_SMS_RING_FINISH:
				gsmEventStatusSmsRingFinish();
				break;
			case GSM_RX_STATUS_SMS_READ_COMMAND:
				gsmEventStatusSmsReadCommand();
				break;			
			case GSM_RX_STATUS_SMS_READ_FINISH:
				gsmEventStatusSmsReadFinish();
				break;			
		}	
	}	
}


// Nie ma rozpocz¹tej operacji
void gsmEventStatusEmpty() {	
	if((strcmp((char*)gsmRxPart,"+CLCC") == 0) && (gsmRxStatus.separator == ':')) {			
		// Dzwoni telefon
		gsmRxStatusSet(GSM_RX_STATUS_RING_BEGIN);
	} else if((strcmp((char*)gsmRxPart,"+CMTI") == 0) && (gsmRxStatus.separator == ':')) {
		// Przyszed³ SMS
		gsmRxStatusSet(GSM_RX_STATUS_SMS_RING_BEGIN);
	} else if((strcmp((char*)gsmRxPart,"+CMGR") == 0) && (gsmRxStatus.separator == ':')) {
		// Odczytaj SMS
		gsmRxStatusSet(GSM_RX_STATUS_SMS_READ_BEGIN);		
	}	
}


// Pocz¹tek dzwonienia telefonu
void gsmEventStatusRingBegin() {
	if((gsmRxStatus.position == 3) && (strcmp((char*)gsmRxPart,"4") != 0)) {
		gsmRxStatusSet(GSM_RX_STATUS_EMPTY);
	}
	// Pozycja zawiera numer telefonu
	if(gsmRxStatus.position == 6) {
		strcpy((char*)gsmRxStatus.phone,(char*)gsmRxPart);
		return;
	}
	// Nowa linia oznacza treœæ wiadomoœci
	if(gsmRxStatus.separator == '\n') {
		gsmRxStatusSet(GSM_RX_STATUS_RING_FINISH);
		return;
	}	
}


// Koniec dzwonienia telefonu
void gsmEventStatusRingFinish() {
	// Wykonaj rozkaz
	DEBUG_SS("RP:",(char *)gsmRxStatus.phone);
	// Wyczyœæ status
	gsmRxStatusSet(GSM_RX_STATUS_EMPTY);
}


// Pocz¹tek przyjœcia smsu
void gsmEventStatusSmsRingBegin() {	
	// Czy pierwsza pozycja zawiera "RECREAD"
	if((gsmRxStatus.position == 1) && (strcmp((char*)gsmRxPart,"SM") != 0)) {
		gsmRxStatusSet(GSM_RX_STATUS_EMPTY);	
		return;
	}
	// Nowa linia oznacza treœæ wiadomoœci
	if(gsmRxStatus.separator == '\n') {
		gsmRxStatusSet(GSM_RX_STATUS_SMS_RING_FINISH);
		return;
	}
}


// Koniec przyjscia smsu
void gsmEventStatusSmsRingFinish() {
	// Wyczyœæ status
	gsmRxStatusSet(GSM_RX_STATUS_EMPTY);
	// Odczytaj sms
	gsmModemText("AT+CMGR=1\r");
}


// Pocz¹tek odczytu smsu
void gsmEventStatusSmsReadBegin() {
	// Druga pozycja zawiera numer telefonu
	if(gsmRxStatus.position == 2) {
		strcpy((char*)gsmRxStatus.phone,(char*)gsmRxPart);
		return;
	}
	// Nowa linia oznacza treœæ wiadomoœci
	if(gsmRxStatus.separator == '\n') {
		gsmRxStatusSet(GSM_RX_STATUS_SMS_READ_NEXT);
		return;
	}	
}


// Nastêpna linia smsu
void gsmEventStatusSmsReadNext() {
	// Czy znaleziono rozkaz
	if((gsmRxStatus.lastSeparator == '(') && (gsmRxStatus.separator == ')')) {
		strcpy((char*)gsmRxStatus.command,(char*)gsmRxPart);
		gsmRxStatusSet(GSM_RX_STATUS_SMS_READ_COMMAND);
		return;
	}
	// Koniec lini oznacza koniec rozkazu
	if(gsmRxStatus.separator == '\n') {
		gsmRxStatusSet(GSM_RX_STATUS_SMS_READ_FINISH);
		return;
	}
}


// Koniec smsu z komend¹
void gsmEventStatusSmsReadCommand() {
	// Koniec lini oznacza koniec rozkazu
	if((gsmRxStatus.lastSeparator == '\n') && (strcmp((char*)gsmRxPart,"OK") == 0) &&(gsmRxStatus.separator == '\n')) {
		// Wykonaj rozkaz
		DEBUG_SS("CP:",(char *)gsmRxStatus.phone);
		DEBUG_SS("CM:",(char *)gsmRxStatus.command);
		// Wyczyœæ status
		gsmRxStatusSet(GSM_RX_STATUS_EMPTY);
		// Usuñ sms
		gsmModemText("AT+CMGD=1\r");
		// Odczytaj nastyêpny sms
		gsmModemText("AT+CMGR=1\r");
	}	
}


// Koniec pustego smsu
void gsmEventStatusSmsReadFinish() {
	if((gsmRxStatus.lastSeparator == '\n') && (strcmp((char*)gsmRxPart,"OK") == 0) &&(gsmRxStatus.separator == '\n')) {
		// Wyczyœæ status
		gsmRxStatusSet(GSM_RX_STATUS_EMPTY);
		// Usuñ sms
		gsmModemText("AT+CMGD=1\r");
		// Odczytaj nastyêpny sms
		gsmModemText("AT+CMGR=1\r");
	}
}

#endif