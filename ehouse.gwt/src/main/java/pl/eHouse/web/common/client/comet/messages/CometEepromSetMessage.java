package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometEepromSetMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;
	private String address;
	private String page;
	private String device;
	private String type;
	private String values;

	public CometEepromSetMessage() {
		super();
	}

	public CometEepromSetMessage(String serial,String address, String page, String type) {
		super();
		this.serial = serial;
		this.address = address;
		this.page = page;
		this.type = type;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}


	@Override
	public String toString() {
		return "CometEepromSetMessage [serial=" + serial + ", address="
				+ address + ", page=" + page + ", device=" + device + ", type="
				+ type + ", values=" + values + "]";
	}

}
