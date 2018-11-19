/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.bootloader;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 *
 * @author Bartek
 */
public class IntelHexLine {
    
    private int count;
    private int high;
    private int low;
    private int type;
    private int data[] = new int [16];
    

    public IntelHexLine(String line) throws IntelHexException {
        if(line == null) {
            throw new IntelHexException("Line is null");
        }        
        if(line.charAt(0) != ':') {
            throw new IntelHexException("Expect char ':' ");
        }
        try {
            count = ConvertUtil.hexToInt(line.substring(1, 3), "Bad count format");
            high = ConvertUtil.hexToInt(line.substring(3, 5), "Bad address format");
            low = ConvertUtil.hexToInt(line.substring(5, 7), "Bad address format");
            type = ConvertUtil.hexToInt(line.substring(7, 9), "Bad type format ");
            int pos = 7;
            for(int i = 0; i < count; ++i) {
                pos = 9 + (i*2);
                data[i] = ConvertUtil.hexToInt(line.substring(pos, pos+2), "Bad data format: " + i);
            }          
            int crc = ConvertUtil.hexToInt(line.substring(pos +2 , pos + 4), "Bad crc format");
            if(crc != countCrc()) {
                throw new IntelHexException("Bad crc");
            }            
        } catch (ConvertException ex) {
            throw new IntelHexException(ex.getMessage());
        }
    }

    
    public IntelHexLine(int type, int address) {
        this.count = 0;
        this.high = address >> 8;
        this.low = address & 0xFF;
        this.type = type;
    }    
 
    
    public int getCount() {
        return count;
    }
    

    public int getAddress() {
        return high << 8 | low;
    }
    
    
    public int getType() {
        return type;
    }
    
    
    public void addData(int value) throws IntelHexException {
        if( count < 16) {
            data[count] = value;
            ++count;            
        } else {
            throw new IntelHexException("Out of bound");
        }
    }
    
    
    public void setData(int i, int value) throws IntelHexException {
        if( i < count ) {
            data[i] = value;
        } else {
            throw new IntelHexException("Out of bound");
        }
    }
    
    
    public int getData(int i) throws IntelHexException {
        if( i < count ) {
            return data[i];
        } else {
            throw new IntelHexException("Out of bound");
        }
    }
    
    public String getFormatedLine() {
        String result = ":" 
                + ConvertUtil.byteToHex(count) 
                + ConvertUtil.byteToHex(high) 
                + ConvertUtil.byteToHex(low)
                + ConvertUtil.byteToHex(type);
        for(int i = 0; i < count; ++i) {
            result += ConvertUtil.byteToHex(data[i]);
        }
        result += ConvertUtil.byteToHex(countCrc());
        return result;
    }
        
    
    @Override
    public String toString() {
        String result =  "<" + ConvertUtil.byteToHex(type) + ">"
                + "["  + ConvertUtil.byteToHex(high) 
                + ConvertUtil.byteToHex(low) + "]";                
        for(int i = 0; i < count; ++i) {
            result += ConvertUtil.byteToHex(data[i]) + ",";
        }        
        return result;
    }
    
    
    private int countCrc() {
        int tmp;
        tmp = count + high + low + type;
        for(int i = 0; i < count; ++i) {
            tmp += data[i];
        }
        tmp = ((tmp % 256) * 0xFF) & 0xFF;
        return tmp;
    }
        
}
