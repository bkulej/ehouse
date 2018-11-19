package pl.eHouse.web.common.client.command;

import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;
import pl.eHouse.web.common.client.utils.SpliterUtils;

/**
 * 
 * Format mappingu: "status","00{off}01{on}other{warning}"
 * 
 */

public class ReceiveMapperStatus extends ReceiveMapper {

	public final static String MAPPING_OTHER = "other";

	private String valueName;
	private String valueMapping;

	public ReceiveMapperStatus(String deviceName, String valueName,
			String valueMapping) {
		super(deviceName);
		this.valueName = valueName;
		this.valueMapping = valueMapping;
	}

	@Override
	public void receiveValues(CometCommandResponse response) {
		if (checkDeviceName(response.getDevice())) {
			if (valueMapping != null && valueName != null) {
				String key = response.getValue(valueName);
				String value = SpliterUtils.mapperSplit(valueMapping, key);
				if (value != null) {
					receiveStatus(value);
					return;
				}
				value = SpliterUtils.mapperSplit(valueMapping, MAPPING_OTHER);
				if (value != null) {
					receiveStatus(value);
				}
			} else {
				receiveStatus(response.getValues().replace(';', ' '));
			}
		}
	}

}
