package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometCommandMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String device;
	private String values;

	public CometCommandMessage() {
		super();
	}

	public CometCommandMessage(String device, String values) {
		super();
		this.device = device;
		this.values = values;
	}

	public String getDevice() {
		return device;
	}

	public String getValues() {
		return values;
	}

	@Override
	public String toString() {
		return "CometCommandMessage [device=" + device + ", values=" + values
				+ "]";
	}

}
