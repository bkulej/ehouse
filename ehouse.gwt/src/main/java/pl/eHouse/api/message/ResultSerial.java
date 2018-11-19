/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.message;

import pl.eHouse.api.utils.ConvertException;

/**
 *
 * @author Bartek
 */
public class ResultSerial extends Result {
    
    protected MessageInSerial messIn;
    protected MessageOutSerial messOut;

    public ResultSerial(MessageOutSerial messOut) {
        this.messOut = messOut;
    }

    public ResultSerial(MessageOutSerial messOut,MessageInSerial messIn) {
        this.messIn = messIn;
        this.messOut = messOut;
    }

    public MessageInSerial getMessIn() {
        return messIn;
    }

    public MessageOutSerial getMessOut() {
        return messOut;
    }
    
    public int getStatus() {
        if(messIn!=null) {
            return STATUS_RESPONSE;
        }
        if(messOut.isBroadcast()) {
            return STATUS_FINISHED;
        }
        return STATUS_NO_REPLY;
    }
    
    @Override
	public String toString() {
		return "<RS>{serial=" + messOut.getSerial() 
				+ ",id=" + messOut.getId() 
				+ ",status="+ getStatusText() + "}";
	}
    
    private String getStatusText() {
        if(messIn!=null) {
            return "RESPONSE";
        }
        if(messOut.isBroadcast()) {
            return "FINISHED";
        }
        return "NO REPLY";
    }
    
    /**
     * Zwrocenie seriala
     * @return 
     */
    public String getSerial() {
    	if(messIn != null) {
    		return messIn.getSerial();
    	} else {
    		return messOut.getSerial();
    	}
    }
    
    /**
     * Zwrocenie adresu
     * @return 
     */
    public Address getAddress() {
        if((messIn != null) && (messIn.getData() != null) && (messIn.getData().length() == 52)) {
            try {
                return new Address(messIn.getData().substring(0, 4));
            } catch (ConvertException ex) {
                return null;
            }
        } else {
            return null;
        }        
    } 
    
    /**
     * Zwrocenie soft type
     * @return 
     */
    public String getSoftwareType() {
        if((messIn != null) && (messIn.getData()!= null) && (messIn.getData().length()==52)) {
            return messIn.getData().substring(4, 8);
        } else {
            return null;
        }
    }
    
    /**
     * Zwrocenie soft ver
     * @return 
     */
    public String getHardwareType() {
        if((messIn != null) && (messIn.getData()!= null) && (messIn.getData().length()==52)) {
            return messIn.getData().substring(8, 12);
        } else {
            return null;
        }        
    }	
    
    /**
     * Zwrocenie wersji
     * @return 
     */
    public String getVersion() {
        if((messIn != null) && (messIn.getData()!= null) && (messIn.getData().length()==52)) {
            return messIn.getDataAsString(12,20);
        } else {
            return null;
        }        
    }
    
    public String getDescription() {
    	return (String)messOut.getTemporary("description");
    }
    
    

}
