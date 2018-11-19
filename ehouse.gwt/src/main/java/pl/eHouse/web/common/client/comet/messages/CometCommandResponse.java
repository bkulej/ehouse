package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.utils.SpliterUtils;

public class CometCommandResponse extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String device;
	private String values;

	public CometCommandResponse() {
		super();
	}

	public CometCommandResponse(String device, String values) {
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

	public String getValue(String key) {
		return SpliterUtils.valueSplit(values, key);
	}

	@Override
	public String toString() {
		return "CometCommandResponse [device=" + device + ", values=" + values
				+ "]";
	}

}
