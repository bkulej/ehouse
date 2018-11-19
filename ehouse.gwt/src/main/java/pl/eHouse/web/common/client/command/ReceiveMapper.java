package pl.eHouse.web.common.client.command;

import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;

public class ReceiveMapper {
	
	private IReceiveMapper widget;
	private String deviceName;
	
	public ReceiveMapper(String deviceName) {
		super();
		this.deviceName = deviceName;
	}
	
	public void receiveValues(CometCommandResponse response) {
		if(checkDeviceName(response.getDevice())) {
			widget.receiveValue(response.getValues());
		}
	}
	
	public boolean checkDeviceName(String device) {
		return deviceName.equals(device) && widget != null;
	}
	
	public IReceiveMapper getWidget() {
		return widget;
	}

	public void setWidget(IReceiveMapper widget) {
		this.widget = widget;
		CommandReceiver.addReceiver(this);
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public void receiveStatus(String status) {
		widget.receiveStatus(status);
	}
	
	public void receiveValue(String value) {
		widget.receiveValue(value);
	}
	
}
