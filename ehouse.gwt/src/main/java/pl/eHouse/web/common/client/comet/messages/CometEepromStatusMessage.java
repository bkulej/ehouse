package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometEepromStatusMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	
	public CometEepromStatusMessage() {
		super();
	}

	public CometEepromStatusMessage(String serial) {
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
		return "CometEepromStatusMessage [serial=" + serial + "]";
	}

}
