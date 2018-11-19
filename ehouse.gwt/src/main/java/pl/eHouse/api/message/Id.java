/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.Random;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 *
 * @author Bartek
 */
public class Id {
    
    private final static int MAXID = 0xFF;
    private static int idTmp = new Random().nextInt(MAXID);
    
    private int id;

    public Id() {
        id = 0;
    }    

    public Id(int id) {
        this.id = id;
    }
    
    public Id(String id) throws ConvertException {
        this.id = ConvertUtil.hexToInt(id, "Bad id");
    }
    
    @Override
    public String toString() {
        return ConvertUtil.byteToHex(id);
    }
    
    /**
     * Generacja id komunikatu
     * @return
     */
    public static Id generate() {
        if(idTmp>=MAXID) {
            idTmp=0;
        }
        ++idTmp;
        return new Id(idTmp);
    }
    
    public void setDigit(char value, int number) {
        this.id |= ConvertUtil.hexToInt(value, (number*4));
    }
    
    public char getDigit(int number) {
        return ConvertUtil.intToDigit(this.id, number);
    }
    
}
