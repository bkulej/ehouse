package pl.eHouse.web.common.server.execute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadMessage;
import pl.eHouse.web.common.client.comet.messages.CometHardwareLoadResponse;

public class HardwareLoadExecutor {

	public static void execute(CometHardwareLoadMessage message,
			GwtAtmosphereResource resource) {
		try {
			ArrayList<Hardware> hardwares = new ArrayList<Hardware>(Config
					.getInstance().getHardwares());
			Collections.sort(hardwares, new Comparator<Hardware>() {
				@Override
				public int compare(Hardware o1, Hardware o2) {
					return o1.getSerial().compareTo(o2.getSerial());
				}
			});
			
			for (Hardware hardware : hardwares) {
				CometHardwareLoadResponse response = new CometHardwareLoadResponse(
						hardware.getSerial(), hardware.getSoftwareType(),
						hardware.getHardwareType());
				response.setAddress(hardware.getAddress().toString());
				response.setDescription(hardware.getDescription());
				resource.post(response);
			}
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}
