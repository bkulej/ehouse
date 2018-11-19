package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometEepromStatusResponse extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String page;
	private String device;

	public CometEepromStatusResponse() {
		super();
	}

	public CometEepromStatusResponse(String serial, String page,
			String device) {
		super();
		this.serial = serial;
		this.page = page;
		this.device = device;
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

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "CometEepromStatusResponse [serial=" + serial + ", page=" + page
				+ ", device=" + device + "]";
	}

}
