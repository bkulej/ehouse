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
public class MessageOutBoot extends MessageOut {

    private String serial;
    private String data; 

    public MessageOutBoot(Id id, Command command, String owner) {
        super(Header.BOOT, id, command, owner);
        this.data = "";
        this.serial = Address.SERIAL_BROADCAST;        
    }

    /**
     * Ustawienie seriala
     *
     * @param serial
     */
    public void setSerial(String serial) {
        if ((serial != null) && (serial.length() == 8)) {
            this.serial = serial;
        }
    }

    public String getSerial() {
        return serial;
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
    public List<Integer> getToSend() {
        List<Integer> mess = new ArrayList<Integer>();
        for (char value : serial.toCharArray()) {
            mess.add((int)value);
        }
        mess.add((int)id.getDigit(1));
        mess.add((int)id.getDigit(0));
        mess.add((int)command.getDigit(1));
        mess.add((int)command.getDigit(0));
        for (char value : data.toCharArray()) {
            mess.add((int)value);
        }
        return mess;
    }
    
    @Override
    public String getKey() {
        return id + "-BOOT";
    }
    
    public boolean isBroadcast() {
        return serial.equals(Address.SERIAL_BROADCAST);
    }

    @Override
    public String toString() {
        return "<OB>{serial=" + serial
                + ",id=" + id
                + ",command=" + command
                + ",data=" + data + "}";
    }
}
