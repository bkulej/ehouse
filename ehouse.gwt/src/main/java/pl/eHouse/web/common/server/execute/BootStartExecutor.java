package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.message.MessageBoot;
import pl.eHouse.web.common.client.comet.messages.CometBootStartMessage;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class BootStartExecutor {

	public static void execute(CometBootStartMessage message,
			GwtAtmosphereResource resource) {
		try {			
			MessageBoot.startRequest(message.getSerial(),GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
