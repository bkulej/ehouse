package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.message.MessageEeprom;
import pl.eHouse.api.message.ResultException;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.api.message.results.ResultEepromStatus;
import pl.eHouse.api.utils.ConvertUtil;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusMessage;
import pl.eHouse.web.common.client.comet.messages.CometEepromStatusResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class EepromStatusExecutor {

	public static void execute(CometEepromStatusMessage message,
			GwtAtmosphereResource resource) {
		try {
			MessageEeprom.statusRequest(message.getSerial(),
					GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultSerial result,
			GwtAtmosphereResource resource) throws ConfigException {
		ResultEepromStatus parser;
		try {
			parser = new ResultEepromStatus(result);
			for (int i = 0; i < parser.getPagesCount(); ++i) {
				CometEepromStatusResponse response = new CometEepromStatusResponse(
						result.getMessIn().getSerial(),
						ConvertUtil.byteToHex(parser.getPage(i)),
						ConvertUtil.byteToHex(parser.getDevice(i)));
				resource.post(response);
			}
		} catch (ResultException e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}

	}
}
