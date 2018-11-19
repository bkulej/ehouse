package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.hardware.Device;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.message.Address;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceSetResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;

public class DeviceSetExecutor {

	public static void execute(CometDeviceSetMessage message,
			GwtAtmosphereResource resource) {
		try {
			Address address = new Address(message.getAddress());
			Hardware hardware = Config.getInstance().getHardware(
					address.getHardwareAddress());
			if (hardware == null) {
				return;
			}
			Device device = hardware.getDevice(address.getDeviceNumber());
			if (device == null) {
				return;
			}
			device.setName(message.getName());
			device.setDescription(message.getDescription());
			Config.getInstance().save(hardware);
			CometDeviceSetResponse response = new CometDeviceSetResponse(
					message.getSerial(), message.getNumber());
			response.setAddress(message.getAddress());
			response.setType(message.getType());
			response.setName(message.getName());
			response.setDescription(message.getDescription());
			resource.post(response);
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}

	}

}
