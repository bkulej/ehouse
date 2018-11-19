package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometDeviceSetMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String serial;	
	private String number;
	private String type;
	private String address;
	private String name;
	private String description;

	public CometDeviceSetMessage() {
		super();
	}

	public CometDeviceSetMessage(String serial, String number) {
		super();
		this.serial = serial;
		this.number = number;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CometDeviceSetMessage [serial=" + serial + ", number=" + number
				+ ", type=" + type + ", address=" + address + ", name=" + name
				+ ", description=" + description + "]";
	}

	

}
