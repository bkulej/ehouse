package pl.eHouse.web.common.client.command;

import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;
import pl.eHouse.web.common.client.utils.SpliterUtils;

/**
 * 
 * Format mappingu: on{command=11;status=00;delay=00;}off{command=11;status=01;delay=00;}other{command=10;}
 * 
 */

public class SendMapper {
	
	public final static String MAPPING_OTHER = "other";

	private String deviceName;
	private String valueMapping;

	public SendMapper(String deviceName, String valueMapping) {
		super();
		this.deviceName = deviceName;
		this.valueMapping = valueMapping;
	}

	public void sendMapping(String key) {
		if (valueMapping != null) {
			String values = SpliterUtils.mapperSplit(valueMapping, key);
			if (values != null) {
				CometSender.send(new CometCommandMessage(deviceName, values));
				return;
			}
			values = SpliterUtils.mapperSplit(valueMapping, MAPPING_OTHER);
			if (values != null) {
				CometSender.send(new CometCommandMessage(deviceName, values));
				return;
			}
		}
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getValueMapping() {
		return valueMapping;
	}

	public void setValueMapping(String valueMapping) {
		this.valueMapping = valueMapping;
	}

}
