package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.bootloader.Software;
import pl.eHouse.api.config.Config;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadResponse;

public class SoftwareLoadExecutor {

	public static void execute(CometSoftwareLoadMessage message,
			GwtAtmosphereResource resource) {
		try {
			for (Software software : Config.getInstance().getSoftwares()) {
				CometSoftwareLoadResponse response = new CometSoftwareLoadResponse(
						software.getId(), software.getType(),
						software.getName(), software.getDescription(),
						software.getDate());
				resource.post(response);
			}
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
