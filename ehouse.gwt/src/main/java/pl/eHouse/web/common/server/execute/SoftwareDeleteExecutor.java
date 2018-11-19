package pl.eHouse.web.common.server.execute;

import java.io.File;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.bootloader.Software;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.Settings;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareDeleteMessage;
import pl.eHouse.web.common.client.comet.messages.CometSoftwareLoadResponse;

public class SoftwareDeleteExecutor {

	public static void execute(CometSoftwareDeleteMessage message,
			GwtAtmosphereResource resource) {
		try {
			new File(Settings.getUploadDirectory() + message.getFileName()).delete();
			Config.getInstance().removeSoftware(message.getId());
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
