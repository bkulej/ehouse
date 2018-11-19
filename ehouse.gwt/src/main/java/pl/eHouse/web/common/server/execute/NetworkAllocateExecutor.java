package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.message.MessageSystem;
import pl.eHouse.api.message.ResultSerial;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class NetworkAllocateExecutor {

	public static void execute(CometNetworkAllocateMessage message,
			GwtAtmosphereResource resource) {
		try {
			Hardware hardware = new Hardware(message.getSerial(),
					message.getSoftware(), message.getSoftware());
			hardware.setAddress(new Address(message.getAddress()));
			hardware.setDescription(message.getDescription());
			MessageSystem.allocateRequest(hardware,
					GwtOwner.get(resource));
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultSerial result,
			GwtAtmosphereResource resource) throws ConfigException {
		CometNetworkAllocateResponse response = new CometNetworkAllocateResponse(
				result.getSerial(), result.getSoftwareType(),
				result.getHardwareType());
		response.setAddress(result.getAddress().toString());
		response.setDescription(result.getDescription());
		resource.post(response);
	}

}
