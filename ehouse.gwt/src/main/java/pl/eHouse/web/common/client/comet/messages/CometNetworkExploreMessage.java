package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometNetworkExploreMessage extends CometMessage {

	private static final long serialVersionUID = 3207605656279677975L;
	
	private String serial;	
	
	public CometNetworkExploreMessage() {
		super();
		this.serial = "00000000";
	}

	public CometNetworkExploreMessage(String serial) {
		super();
		this.serial = serial;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	@Override
	public String toString() {
		return "CometNetworkExploreMessage [serial=" + serial + "]";
	}
	
}
