/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.listeners;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.message.MessageInBoot;
import pl.eHouse.api.message.MessageInSerial;

/**
 * Klasa bufora 
 * @author Bartek
 */
public interface IInputListener {

    public void messageIn(MessageInAddress mess) throws ListenerException;   
    
    public void messageIn(MessageInSerial mess) throws ListenerException;
    
    public void messageIn(MessageInBoot mess) throws ListenerException;
    
    public String getId();
    
}
