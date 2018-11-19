package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.hardware.Device;
import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertUtil;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadFinish;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometDeviceLoadResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;

public class DeviceLoadExecutor {

	public static void execute(CometDeviceLoadMessage message,
			GwtAtmosphereResource resource) {
		try {
			for (Device device : Config.getInstance()
					.getHardware(new Address(message.getAddress()))
					.getDevices()) {
				CometDeviceLoadResponse response = new CometDeviceLoadResponse(
						device.getHardware().getSerial(),
						ConvertUtil.byteToHex(device.getNumber()),
						ConvertUtil.byteToHex(device.getType()));
				response.setTypeName(DeviceTypes.getName(device.getType()));
				response.setAddress(device.getAddress().toString());
				response.setName(device.getName());
				response.setDescription(device.getDescription());
				resource.post(response);
			}
			resource.post(new CometDeviceLoadFinish());
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}

	}

}
