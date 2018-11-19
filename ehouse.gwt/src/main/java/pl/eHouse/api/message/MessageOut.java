/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Bartek
 */
public abstract class MessageOut {
    
    public final static int DEFAULT_REPEAT_COUNT    = 3;
    public final static int DEFAULT_REPEAT_PERIOD   = 500;
    
    protected String owner;
    protected int type;
    protected Id id;
    protected Command command;
    protected int repeatCount;
    protected int repeatPeriod;
    protected HashMap<String, Object> temporary = new HashMap<String, Object>();

    public MessageOut(int type, Id id, Command command, String owner) {
        this.type = type;
        this.id = id;
        this.command = command;
        if(command.isRequest()) {
            this.repeatCount = DEFAULT_REPEAT_COUNT;
            this.repeatPeriod = DEFAULT_REPEAT_PERIOD;
        } else {
            this.repeatCount = 1;
            this.repeatPeriod = DEFAULT_REPEAT_PERIOD;
        }
        this.owner = owner;
    }
    
    public void setRepeat(int count, int periodMs) {
        this.repeatCount = count;
        this.repeatPeriod = periodMs;
    }

    public Command getCommand() {
        return command;
    }

    public Id getId() {
        return id;
    }

    public int getType() {
        return type;
    }
 
    public int getRepeatPeriod() {
        return repeatPeriod;
    }    
    
    public int getRepeatCount() {
		return repeatCount;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setTemporary(String key, Object value) {
    	temporary.put(key, value);
    }
    
    public Object getTemporary(String key) {
    	return temporary.get(key);
    }
    
    public abstract String getKey();
    
    public abstract List<Integer> getToSend();
    
    @Override
    public abstract String toString();
    
}
