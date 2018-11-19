package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometDeviceLoadMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String address;

	public CometDeviceLoadMessage() {
		super();
	}

	public CometDeviceLoadMessage(String address) {
		super();
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CometDeviceLoadMessage [address=" + address + "]";
	}

	

}
