/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 *
 * @author Bartek
 */
public class Address {
    
    public final static String SERIAL_BROADCAST = "00000000";
    public final static Address ADDRESS_BROADCAST = new Address(0x0000);
    public final static Address ADDRESS_COMPUTER = new Address(0x0001); 
    public final static Address ADDRESS_EMPTY = new Address(0xFFFF);
    
    private int address;
    
    public Address() {
        this.address = 0;
    }

    public Address(int address) {
        this.address = address;
    }
    
    public Address(String address) throws ConvertException {        
        this.address = ConvertUtil.hexToInt(address, "Bad address");
    }

    public int getInt() {
        return address;
    }
    
    public Address getHardwareAddress() {
    	return new Address(getHardwareNumber());
    }
    
    public int getHardwareNumber() {
    	return address & 0xFFF0;
    }
    
    public int getDeviceNumber() {
    	return address & 0xF;
    }        
    
    public boolean equals(Address address) {
        return this.address == address.getInt();
    }
    
    public boolean equals(Integer address) {
        return this.address == address.intValue();
    }    
    
    public static boolean checkAddress(MessageInAddress mess) {
        return mess.getAdd().equals(ADDRESS_COMPUTER) || mess.getAdd().equals(ADDRESS_BROADCAST);
    }
    
    public void setDigit(char value, int number) {
        this.address |= ConvertUtil.hexToInt(value, (number*4));
    }
    
    public char getDigit(int number) {
        return ConvertUtil.intToDigit(this.address, number);
    }
    
    @Override
    public String toString() {
        return ConvertUtil.wordToHex(address);
    }
    
}
