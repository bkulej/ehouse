package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.message.MessageSystem;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometRestartMessage;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class RestartExecutor {

	public static void execute(CometRestartMessage message,
			GwtAtmosphereResource resource) {
		try {			
			MessageSystem.restartRequest(message.getSerial(),GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
