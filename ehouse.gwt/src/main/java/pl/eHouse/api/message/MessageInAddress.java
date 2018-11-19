/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 *
 * @author Bartek
 */
public class MessageInAddress {
    
    private int type;
    private Address add;
    private Address asd;
    private Id id;
    private Command command;
    private String data;
    private int byteCounter;    

    /**
     * Konstruktor
     */
    public MessageInAddress(MessageIn mess) {  
        add = new Address();
        asd = new Address();
        id = new Id();
        command = new Command();
        data = "";
        byteCounter = 0;
        for(Integer part : mess.getParts()) {
            resolvePart((char)part.intValue());
        }
    }

    public Address getAdd() {
        return add;
    }

    public Address getAsd() {
        return asd;
    }

    public Id getId() {
        return id;
    }

    public Command getCommand() {
        return command;
    }

    public int getType() {
        return type;
    }  
    
    public String getData() {
        return data;
    }
    
    public int getDataWordAsInt(int position) {
		try {
			return ConvertUtil.hexToInt(data.substring(position, position + 4),
					"Conversion error");
		} catch (ConvertException ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return 0;
		}
	}

	public Address getDataWordAsAddress(int position) {
		try {
			return new Address(data.substring(position, position + 4));
		} catch (ConvertException ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return null;
		}
	}

	public String getDataWordAsString(int position) {
		return data.substring(position, position + 4);
	}

	public int getDataByteAsInt(int position) {
		try {
			return ConvertUtil.hexToInt(data.substring(position, position + 2),
					"Conversion error");
		} catch (Exception ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return 0;
		}
	}

	public String getDataByteAsString(int position) {
		return data.substring(position, position + 2);
	}

	public char getDataAsChar(int position) {
		try {
			return data.charAt(position);
		} catch (Exception ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return ' ';
		}
	}

	public String getDataString(int position, int length) {
		try {
			return data.substring(position, position + length);
		} catch (Exception ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return "";
		}
	}
    
    /** Sprawdzenie czy wiadomośc skierowana do komputera 
     * @return 
     */
    public boolean isForMe() {
        return add.getInt() == Address.ADDRESS_COMPUTER.getInt() 
                || add.getInt() == Address.ADDRESS_BROADCAST.getInt();
    }
    
    /**
     * Pobranie klucza do tablicy Hash z adresu zrodłowego
     * @return 
     */
    public String getKey() {
        return id + "-ADDRESS";
    }
    
    /**
     * Dodanie
     * @param data
     * @return
     */
    private void resolvePart(char part) {                
        // Dodanie czesci
        switch(++byteCounter) {            
            case 1: add.setDigit(part,3);
            break;
            case 2: add.setDigit(part,2);
            break;
            case 3: add.setDigit(part,1);
            break;
            case 4: add.setDigit(part,0);
            break;
            case 5: asd.setDigit(part,3);
            break;
            case 6: asd.setDigit(part,2);
            break;
            case 7: asd.setDigit(part,1);
            break;
            case 8: asd.setDigit(part,0);
            break;
            case 9: id.setDigit(part,1);
            break;
            case 10: id.setDigit(part,0);
            break;
            case 11: command.setDigit(part,1);
            break;
            case 12: command.setDigit(part,0);
            break;
            default: data += part;
        }        
    }
    
    @Override
    public String toString() {               
        return "<IA>{add=" + add 
                + ",asd=" + asd 
                + ",id=" + id 
                + ",command=" + command 
                + ",data=" + data + "}";
    }

        
}
