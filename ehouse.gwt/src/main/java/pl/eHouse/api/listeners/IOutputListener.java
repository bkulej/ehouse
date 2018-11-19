/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.listeners;

import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.api.message.MessageOutBoot;
import pl.eHouse.api.message.MessageOutSerial;

/**
 * Klasa bufora 
 * @author Bartek
 */
public interface IOutputListener {
    
    public void messageOut(MessageOutAddress mess) throws ListenerException;
    
    public void messageOut(MessageOutSerial mess) throws ListenerException;
    
    public void messageOut(MessageOutBoot mess) throws ListenerException;
    
    public String getId();
    
}
