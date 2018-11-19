package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.Device;
import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.message.MessageEeprom;
import pl.eHouse.api.message.ResultException;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.api.message.results.ResultEepromGet;
import pl.eHouse.api.utils.ConvertUtil;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromGetResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class EepromGetExecutor {

	public static void execute(CometEepromGetMessage message,
			GwtAtmosphereResource resource) {
		try {
			MessageEeprom.getRequest(message.getSerial(),
					ConvertUtil.hexToInt(message.getPage()),
					GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultSerial result,
			GwtAtmosphereResource resource) throws ConfigException,
			ResultException {
		CometEepromGetResponse response = new CometEepromGetResponse(
				ResultEepromGet.getSerial(result),
				ConvertUtil.byteToHex(ResultEepromGet.getPage(result)),
				ConvertUtil.byteToHex(ResultEepromGet.getDevice(result)),
				ConvertUtil.byteToHex(getDeviceType(result)));
		response.setValues(ResultEepromGet.prepare(getDeviceType(result),
				result));
		resource.post(response);

	}

	private static int getDeviceType(ResultSerial result)
			throws ConfigException {
		Hardware hardware = Config.getInstance()
				.getHardware(result.getSerial());
		if (hardware == null) {
			return DeviceTypes.TYPE_EMPTY;
		}
		int iDevice = result.getMessIn().getDataByteAsInt(2);
		if (iDevice > 0x0F) {
			return DeviceTypes.TYPE_EMPTY;
		}
		Device device = hardware.getDevice(iDevice);
		if (device == null) {
			return DeviceTypes.TYPE_EMPTY;
		}
		return device.getType();
	}

}
