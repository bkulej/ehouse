/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.message;


/**
 *
 * @author Bartek
 */
public class ResultAddress extends Result {    
    
    protected MessageInAddress messIn;
    protected MessageOutAddress messOut;

    public ResultAddress(MessageOutAddress messOut) {
        this.messOut = messOut;
    }

    public ResultAddress(MessageOutAddress messOut, MessageInAddress messIn) {
        this.messIn = messIn;
        this.messOut = messOut;
    }

    public MessageInAddress getMessIn() {
        return messIn;
    }

    public MessageOutAddress getMessOut() {
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
		return "<RA>{address=" + messOut.getAdd().toString() 
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

}
