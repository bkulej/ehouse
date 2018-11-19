package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometBootFindMessage extends CometMessage {

	private static final long serialVersionUID = 3207605656279677975L;
	
	private String serial;	
	
	public CometBootFindMessage() {
		super();
		this.serial = "00000000";
	}

	public CometBootFindMessage(String serial) {
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
		return "CometBootFindMessage [serial=" + serial + "]";
	}
	
}
