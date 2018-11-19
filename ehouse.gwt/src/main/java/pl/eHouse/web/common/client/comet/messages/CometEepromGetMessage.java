package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometEepromGetMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String page;

	public CometEepromGetMessage() {
		super();
	}

	public CometEepromGetMessage(String serial, String page) {
		super();
		this.serial = serial;
		this.page = page;
	}
	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "CometEepromGetMessage [serial=" + serial + ", page=" + page
				+ "]";
	}

}
