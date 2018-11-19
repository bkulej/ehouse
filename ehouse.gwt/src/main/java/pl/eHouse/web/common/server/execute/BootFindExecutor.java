package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.message.MessageBoot;
import pl.eHouse.api.message.ResultBoot;
import pl.eHouse.api.utils.ConvertUtil;
import pl.eHouse.web.common.client.comet.messages.CometBootFindMessage;
import pl.eHouse.web.common.client.comet.messages.CometBootFindResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class BootFindExecutor {

	public static void execute(CometBootFindMessage message,
			GwtAtmosphereResource resource) {
		try {
			MessageBoot.findRequest(message.getSerial(), GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultBoot result,
			GwtAtmosphereResource resource) throws ConfigException {
		CometBootFindResponse response = new CometBootFindResponse(
				result.getSerial(), ConvertUtil.wordToHex(result
						.getHardwareType()), ConvertUtil.wordToHex(result
						.getMemorySize()), ConvertUtil.wordToHex(result
						.getPageSize()),result.getHardwareStatus());
		resource.post(response);
	}
}
