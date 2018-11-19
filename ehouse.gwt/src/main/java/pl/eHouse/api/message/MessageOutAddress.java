/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertUtil;

/**
 *
 * @author Bartek
 */
public class MessageOutAddress extends MessageOut{
            
    private Address add;
    private Address asd;    
    private String data;    
           
    public MessageOutAddress(Id id, Command command, String owner) {
        super(Header.ADDRESS, id, command, owner);    
        this.add = Address.ADDRESS_BROADCAST;
        this.asd = Address.ADDRESS_COMPUTER;        
        this.data = "";    
        if(!command.isRequest()) {
            setRepeat(1, MessageOut.DEFAULT_REPEAT_PERIOD);
        }
    }
    
    public void setAdd(Address add) {
        this.add = add;        
    }    

    public Address getAdd() {
		return add;
	}

	public void setAsd(Address asd) {
        this.asd = asd;
    }
	
	public Address getAsd() {
		return asd;
	}
        
    public void addDataString(String value) {
        this.data += value!=null ? value: "";
    }
    
    public void addDataByte(int value) {
        this.data += ConvertUtil.byteToHex(value);
    }
    
    public void addDataWord(int value) {
        this.data += ConvertUtil.wordToHex(value);
    }
    
    public void addDataChar(char value) {
        this.data += value;
    }    
    
    @Override
    public String getKey() {
        return id + "-ADDRESS";
    }
    
    @Override
    public List<Integer> getToSend() {    
        List<Integer> mess = new ArrayList<Integer>();
        mess.add((int)add.getDigit(3));
        mess.add((int)add.getDigit(2));
        mess.add((int)add.getDigit(1));
        mess.add((int)add.getDigit(0));
        mess.add((int)asd.getDigit(3));
        mess.add((int)asd.getDigit(2));
        mess.add((int)asd.getDigit(1));
        mess.add((int)asd.getDigit(0));
        mess.add((int)id.getDigit(1));
        mess.add((int)id.getDigit(0));
        mess.add((int)command.getDigit(1));
        mess.add((int)command.getDigit(0));
        for(char value : data.toCharArray()) {
            mess.add((int)value);
        }
        return mess;        
    }
    
    public boolean isBroadcast() {
        return add.equals(Address.ADDRESS_BROADCAST);
    }
            
    @Override
    public String toString() {        
        return "<OA>{add=" + add 
                + ",asd=" + asd 
                + ",id=" + id
                + ",command=" + command 
                + ",data=" + data + "}";
    }
    
}
