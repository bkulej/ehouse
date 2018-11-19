package pl.eHouse.web.common.client.command;

import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;

/**
 * 
 */

public class ReceiveMapperValue extends ReceiveMapper {

	private String valueName;

	public ReceiveMapperValue(String deviceName, String valueName) {
		super(deviceName);
		this.valueName = valueName;
	}

	@Override
	public void receiveValues(CometCommandResponse response) {
		String value = response.getValue(valueName);
		if (checkDeviceName(response.getDevice()) && value != null) {
			receiveValue(value);
		}
	}

}
