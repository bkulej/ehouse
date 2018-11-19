package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.message.Address;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressMessage;
import pl.eHouse.web.common.client.comet.messages.CometFreeAddressResponse;

public class FreeAddressExecutor {
	
	private final static int max = 20;	

	public static void execute(CometFreeAddressMessage message, GwtAtmosphereResource resource) {
		try {
			CometFreeAddressResponse response = new CometFreeAddressResponse();
			int count =0;
			for (Address address : Config.getInstance().getFreeAddresses()) {
				response.addAddress(address.toString());
				if(++count > max ) {
					break;
				}
			}
			resource.post(response);
		} catch (ConfigException e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}

	}

}
