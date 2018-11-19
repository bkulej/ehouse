package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.CommunicatorException;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.message.MessageSystem;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkExploreResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class NetworkExploreExecutor {

	public static void execute(CometNetworkExploreMessage message,
			GwtAtmosphereResource resource) {
		try {
			MessageSystem.findRequest(message.getSerial(),
					GwtOwner.get(resource));
		} catch (CommunicatorException e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultSerial result,
			GwtAtmosphereResource resource) throws ConfigException {
		String description = "";
		Hardware hardware = Config.getInstance().getHardware(
				result.getAddress());
		if ((hardware != null)
				&& hardware.getSerial().equals(result.getSerial())) {
			description = hardware.getDescription();
		}
		CometNetworkExploreResponse response = new CometNetworkExploreResponse(
				result.getSerial(), result.getSoftwareType(),
				result.getHardwareType(), result.getVersion());
		response.setAddress(result.getAddress().toString());
		response.setDescription(description);
		resource.post(response);
	}
}
