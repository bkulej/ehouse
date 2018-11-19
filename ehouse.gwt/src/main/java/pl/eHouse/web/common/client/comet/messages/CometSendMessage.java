package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometSendMessage extends CometMessage {

	private static final long serialVersionUID = 3207605656279677975L;
	
	private String command;	
	private String address;
	private String data;
	
	public CometSendMessage() {
		super();
	}

	public CometSendMessage(String command, String address, String data) {
		super();
		this.command = command;
		this.address = address;
		this.data = data;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CometSendMessage [command=" + command + ", address=" + address
				+ ", data=" + data + "]";
	}
	
}
