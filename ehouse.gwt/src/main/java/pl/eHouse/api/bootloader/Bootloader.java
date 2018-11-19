/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.bootloader;

import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.CommunicatorException;
import pl.eHouse.api.listeners.IResultlListener;
import pl.eHouse.api.listeners.ListenerException;
import pl.eHouse.api.message.MessageBoot;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.Result;
import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.api.utils.ConvertException;

/**
 *
 * @author Bartek
 */
public class Bootloader implements IResultlListener{
    
    public final static int RESULT_OK = 0;
    public final static int RESULT_CANCELED = 1;
    public final static int RESULT_ERROR = 2;
    
    private static Bootloader bootloader;
    private BootloaderListener listener;
    private CodeMemory code;
   
    private String serial;   
    private int pageSize;
    private int pageCount;
    private int pageNumber;
    
    
    /*
     * Konstruktor
     */
    private Bootloader(String serial, int memorySize, int pageSize, IntelHexFile file, BootloaderListener listener) 
            throws BootloaderException, IntelHexException {   
        if((serial == null) || (serial.length() != 8)) {
            throw new BootloaderException("Bad serial");
        }
        if((memorySize == 0) || ((memorySize % 512) != 0)) {
            throw new BootloaderException("Bad memory size");
        }
        if((pageSize == 0) || ((pageSize % 16) != 0)) {
            throw new BootloaderException("Bad page size");
        }        
        if(file == null) {
            throw new BootloaderException("Bad file");
        }        
        if(listener == null) {
            throw new BootloaderException("Bad listener");
        }
        if((memorySize % pageSize) != 0) {
            throw new BootloaderException("Bad configuration of memory and page size");
        }
        this.serial = serial;        
        this.pageSize = pageSize;
        this.listener = listener;        
        this.pageCount = memorySize / pageSize;
        this.code = new CodeMemory(file);        
    }
     
    
    /**
     * Uruchomienie bootloadera
     * @param serial
     * @param memorySize
     * @param pageSize
     * @param file
     * @param listener
     * @throws BootloaderException
     * @throws CommunicatorException
     * @throws ConvertException
     * @throws IntelHexException 
     */
    public synchronized static void load(String serial, int memorySize, int pageSize, IntelHexFile file, BootloaderListener listener) 
            throws BootloaderException, CommunicatorException, ConvertException, IntelHexException {
        if(bootloader == null) {
            bootloader = new Bootloader(serial, memorySize, pageSize, file, listener);            
            bootloader.start();
        } else {
            throw new BootloaderException("Bootloader is busy");
        }        
    }
    
    
    /** 
     * Zatrzymanie bootloadera
     * @throws BootloaderException 
     */
    public synchronized static void cancel() throws BootloaderException {
        if(bootloader != null) {
            try {
				bootloader.stop(Bootloader.RESULT_CANCELED);
			} catch (CommunicatorException e) {
				throw new BootloaderException(e.getMessage());
			}
        } else {
            throw new BootloaderException("Bootloader is not started");
        }
    }    
        
    
    /*
     * Uruchomienie bootloadera
     */
    private void start() 
            throws CommunicatorException, ConvertException, BootloaderException {              
        Communicator.getInstance().registerListenerResult(bootloader);
        listener.start(pageCount);        
        MessageBoot.beginRequest(serial, getId());
    }
    
    
    /*
     * Zatrzymanie bootloadera
     */
    private void stop(int result) throws CommunicatorException {
        Communicator.getInstance().unRegisterListenerResult(bootloader);
        bootloader = null;
        listener.end(result);        
    }           
        

    /*
     * Obsuga zakonczenia komunikatu
     */
    public void result(ResultBoot result) 
            throws ListenerException {    	
        if(serial.equals(result.getMessOut().getSerial())) {
            // Brak odpowiedzi
            if((result.getStatus() != Result.STATUS_RESPONSE) 
                    || (result.getMessIn().getCommand().equals(MessageBoot.ERROR_RESPONSE))) {
                try {
					stop(Bootloader.RESULT_ERROR);
				} catch (CommunicatorException e) {
					throw new ListenerException(e.getMessage());
				}                
            } else {
                try {
                    decodeResponse(result.getMessOut());
                } catch (Exception ex) {
                    try {
						stop(Bootloader.RESULT_ERROR);
					} catch (CommunicatorException e) {
						throw new ListenerException(e.getMessage());
					}                
                    Logger.getLogger(Bootloader.class.getName()).log(Level.SEVERE, null, ex);                    
                }
            }
        }
    }
    
    
    /*
     * Dekodowanie zakonczonej wiadomosci
     */
    private void decodeResponse(MessageOutBoot messOut) 
            throws CommunicatorException, ConvertException, IntelHexException {  
        // Jezeli poczatek to wyzeruj 
        if(MessageBoot.BEGIN_REQUEST.equals(messOut.getCommand())){
            pageNumber = 0;
            pageSend();            
        } else if(MessageBoot.SAVE_REQUEST.equals(messOut.getCommand())) { 
            pageSend();                        
        } else if(MessageBoot.END_REQUEST.equals(messOut.getCommand())) {
            stop(Bootloader.RESULT_OK);
        }
    }
    
    
    /*
     * Wyslanie komunikatu SAVE lub END
     */
    private void pageSend() throws CommunicatorException, ConvertException, IntelHexException {
        if(pageNumber < pageCount) {
            int address = pageNumber * pageSize;            
            MessageBoot.saveRequest(serial, address, code.getPage(address, pageSize), getId());
            listener.progres(pageNumber);
        } else {
            MessageBoot.endRequest(serial, getId());
        }
        ++pageNumber;        
    }
                   
    
    @Override
    public void result(ResultAddress result) throws ListenerException {        
    }
    
    @Override
	public void result(ResultSerial result) throws ListenerException {		
	}


	@Override
	public String getId() {
		return "BOOTLOADER-0";
	}
     
}
