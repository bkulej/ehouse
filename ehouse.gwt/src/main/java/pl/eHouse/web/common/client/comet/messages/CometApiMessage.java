package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometApiMessage extends CometMessage {

	private static final long serialVersionUID = 4207606548962710686L;
	
	private String value;	

	private String date;
	
	public CometApiMessage() {
		super();
	}

	public CometApiMessage(String value, String date) {
		super();
		this.value = value;
		this.date = date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}		

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CometApiMessage [value=" + value + "]";
	}
		
}
