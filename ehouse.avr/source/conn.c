#define CONN_BODY

#include "devices.h"
#include "debug.h"
#include "conn.h"
#include "mess.h"
#include "utils.h"
#include "event.h"
#include "timer.h"
#include "address.h"
#include "decoder.h"
#include "channel1.h"
#include <avr/io.h>
#include <util/delay.h>


#ifdef CONN_MODULE

// Tablica struktur z informacjami o po³¹czeniach
typedef struct {
	uint8_t type;										// Typ polaczenia
	uint16_t address;									// Adres			
	uint8_t device;										// Adres zrod³owy urzadzenia	
	uint8_t id;											// id wiadomosci	
	uint8_t com;										// comand
	uint8_t param1;										// Parametr1
	uint8_t param2;										// Parametr2
	uint8_t toWait;										// ilosc przerwan do czekania na oproznienie
	uint8_t toSend;										// ilosc przerwan do czekania na oproznienie
} ConnStruct;



volatile ConnStruct connRequests[CONN_MAX_CONN];		// Bufor komunikatów


/* Wyszukuje polaczenie
 */
int8_t connFind(uint8_t id, uint16_t ad) {
	// Wyszukaj zadane po³aczenie
	for(uint8_t i = 0; i<CONN_MAX_CONN; i++) {			
		// Czy znaleziono po³aczenie
		if ((connRequests[i].id == id) && (connRequests[i].address == ad)) {			
			return i;			
		}		
	}	 	
	// Nie znaleziono po³aczenia
	return CONN_NOT_FOUND;
}


/* Tworzy nowe po³aczenie
 */
int8_t connCreate(uint8_t type, uint16_t address, uint8_t device, uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
	// Cykliczny licznik 
	static uint8_t connCycle = 0;
	// Wyszukaj wolne polaczenie na pustym id
	for(uint8_t i = 0; i<CONN_MAX_CONN; i++) {			
		uint8_t conn = (++connCycle) % CONN_MAX_CONN;			
		// Zarezerwuj komorke
		connRequests[conn].type = type;
		connRequests[conn].address = address;	
		connRequests[conn].device = device;			
		connRequests[conn].id = id;		
		connRequests[conn].com = com;
		connRequests[conn].param1 = param1;
		connRequests[conn].param2 = param2;
		if(type == CONN_TYPE_SEND) {
			connRequests[conn].toWait = CONN_WAIT_TO_REPEAT;
			connRequests[conn].toSend = CONN_COUNT_TO_REPEAT;
		} else {
			connRequests[conn].toWait = 0;
			connRequests[conn].toSend = 0;
		}
		// W³acz timer
		timerStart(TIMER_CONN);
		// Zwróc zanaczone po³aczenie			
		return conn;
	}	
	// Nie znaleziono wolnego polaczenia	
	return CONN_NOT_FOUND;
}


/* Wiadomosc przys³ana
*/
void connReceive() {	
	// Pobranie danych
	uint16_t add = ADDRESS_EMPTY;
	uint16_t asd = ADDRESS_COMPUTER;
	uint8_t id = MESS_GET_ID();
	uint8_t com = MESS_GET_COM();
	uint8_t ndd = 0x00;
	uint8_t type = MESS_GET_TYPE();
	uint8_t param1 = MESS_GET_BYTE(0);
	uint8_t param2 = MESS_GET_BYTE(1);
	if(type == MESS_TYPE_ADDRESS) {
		add = MESS_GET_ADD();
		asd = MESS_GET_ASD();
		ndd = MESS_GET_NDD();
	}
	// Debug
	//DEBUG_S("****");
	//DEBUG_SNN("CR add,asd:", add, asd);
	//DEBUG_SNN("CR com,ndd:", com, ndd);
	// Sprawdzenie adresu
	if(!addressCheck(type,add)) {
		return;
	}	
	// Wyszukaj polaczenie
	int8_t conn = connFind(id,asd);	
	// Dekoduj wstepnie
	uint8_t request = decoderIsRequest(com);
	if((type == MESS_TYPE_ADDRESS) && request && addressCheck(type,add)) {
		// ------------------ REQUEST MAIN---------------------------
		DEBUG_SNN("CR-M i,c:",id,com);
		decoderRequestReceive(ndd,com,param1,param2);
		if(!addressIsBroadcast(add)) {
			decoderResponseSend(asd,ndd,id,com,param1,param2);
		}		
	} else if(request) {
		// ------------------ REQUEST OTHER---------------------------
		DEBUG_SNN("CR-O i,c:",id,com);
		// Czy takie polaczenie juz istnieje lub istnialo
		if(conn == CONN_NOT_FOUND) {
			// Rozpocznij nowe po³¹czenie przychodz¹ce
			conn = connCreate(CONN_TYPE_RECEIVE,asd,ndd,id,com,MESS_GET_BYTE(0),MESS_GET_BYTE(0));
			// Czy znaleziono nowe polaczenie
			if (conn < 0) {
				return;
			}
			// Wykonaj request
			decoderRequestReceive(ndd,com,param1,param2);
		}
		// Wyslij odpowiedz
		if(connRequests[conn].type == CONN_TYPE_RECEIVE) {
			// Wyslij odpowiedz
			decoderResponseSend(asd,ndd,id,com,param1,param2);
		}
	} else {
		// ------------------ RESPONSE ---------------------------
		DEBUG_SNN("CR-R i,c:",id,com);
		// Czy polaczenie jest aktywne
		if (conn != CONN_NOT_FOUND) {
			// Zakoncz polaczenie
			connRequests[conn].type = CONN_TYPE_EMPTY;
			// Wykonaj response
			decoderResponseReceive(asd,ndd,id,com,param1,param2);
		}
	}					
}


/* Obs³uga ponownego wyslania
 */
void connEvent(void) {
	// Ilosc aktywnych po³aczeñ ustaw na zero
	uint8_t activeConn = 0;
	// PrzejdŸ po polaczeniach
	for(uint8_t i = 0; i<CONN_MAX_CONN; i++) {
		// Czy po³aczeni jest wychodzace
		if(connRequests[i].type == CONN_TYPE_SEND) {			
			if (connRequests[i].toWait) {
				// Zmniejsz oczekiwanie	
				-- connRequests[i].toWait;
				++activeConn;				
			} else if (connRequests[i].toSend) {
				// Wyslij ponownie wiadmosc				
				messAddressSend(MESS_MODE_REPEAT | MESS_MODE_END, 
					connRequests[i].address,
					connRequests[i].device,
					connRequests[i].id,
					connRequests[i].com,
					connRequests[i].param1,
					connRequests[i].param2);
					--connRequests[i].toSend;	
				connRequests[i].toWait = CONN_WAIT_TO_REPEAT;
				++activeConn;
			} else {
				// Zakonczono polaczenie
				connRequests[i].type = CONN_TYPE_EMPTY;
				decoderResponseReceive(connRequests[i].address,
					connRequests[i].device,
					connRequests[i].id,
					DECODER_RESPONSE_ERROR,
					connRequests[i].param1,
					connRequests[i].param2);
			}												
		}
	}
	// Czy sa jakies otwarte po³aczenia
	if(!activeConn) {
		// Wy³¹cz timer
		timerStop(TIMER_CONN);
	} 
}


#endif

