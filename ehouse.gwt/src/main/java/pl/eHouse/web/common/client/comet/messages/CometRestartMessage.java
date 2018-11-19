package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometRestartMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String software;
	private String hardware;

	public CometRestartMessage() {
		super();
	}

	public CometRestartMessage(String serial, String software, String hardware) {
		super();
		this.serial = serial;
		this.software = software;
		this.hardware = hardware;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	@Override
	public String toString() {
		return "CometRestartMessage [serial=" + serial + ", software="
				+ software + ", hardware=" + hardware + "]";
	}

}
