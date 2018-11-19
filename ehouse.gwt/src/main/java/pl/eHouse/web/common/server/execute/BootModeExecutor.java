package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.message.MessageSystem;
import pl.eHouse.web.common.client.comet.messages.CometBootModeMessage;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class BootModeExecutor {

	public static void execute(CometBootModeMessage message,
			GwtAtmosphereResource resource) {
		try {			
			MessageSystem.bootRequest(message.getSerial(),GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
