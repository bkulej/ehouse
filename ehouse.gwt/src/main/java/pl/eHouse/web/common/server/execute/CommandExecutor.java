package pl.eHouse.web.common.server.execute;

import java.util.List;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.Device;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.message.MessageCommand;
import pl.eHouse.api.message.ResultAddress;
import pl.eHouse.api.message.ResultException;
import pl.eHouse.api.message.params.ParamCommand;
import pl.eHouse.api.message.results.ResultCommand;
import pl.eHouse.api.utils.ParamSpliter;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;
import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class CommandExecutor {

	public static void execute(CometCommandMessage message,
			GwtAtmosphereResource resource) {
		try {
			Device device = Config.getInstance().getDevice(message.getDevice());
			if (device != null) {
				ParamSpliter values = new ParamSpliter(message.getValues());
				int command = values.getValueHexAsInt("command");
				List<Integer> params = ParamCommand.prepare(device.getType(),
						device.getNumber(), values);
				MessageCommand.request(device.getAddress(), command, params,
						GwtOwner.get(resource));
			}
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void response(ResultAddress result,
			GwtAtmosphereResource resource) throws ConfigException,
			ResultException {
		Device device = getDevice(result);
		if (device != null) {
			resource.post(new CometCommandResponse(device.getName(),
					ResultCommand.prepare(device.getType(), result)));
		}

	}

	private static Device getDevice(ResultAddress result)
			throws ConfigException {
		Address address = result.getMessIn().getAsd();
		if (address == null) {
			return null;
		}
		return Config.getInstance().getDevice(address);
	}

}
