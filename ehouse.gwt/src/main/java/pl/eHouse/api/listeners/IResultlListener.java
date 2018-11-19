/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.listeners;

import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.message.ResultSerial;

/**
 * Klasa bufora 
 * @author Bartek
 */
public interface IResultlListener {
    
    public void result(ResultAddress result) throws ListenerException;   
    
    public void result(ResultSerial result) throws ListenerException; 
    
    public void result(ResultBoot result) throws ListenerException;
    
    public String getId();
       
}
