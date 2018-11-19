package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometDeviceLoadFinish extends CometMessage {

	private static final long serialVersionUID = 4207606548962710686L;
	
	public CometDeviceLoadFinish() {
		super();
	}

	@Override
	public String toString() {
		return "CometDeviceLoadFinish []";
	}
		
}
