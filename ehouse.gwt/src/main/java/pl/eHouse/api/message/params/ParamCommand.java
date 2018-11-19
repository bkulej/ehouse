package pl.eHouse.api.message.params;

import java.util.List;

import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamCommand {

	public static List<Integer> prepare(int deviceType, int device,
			ParamSpliter params) throws ConvertException {
		switch (deviceType) {
		case DeviceTypes.TYPE_OUTPUT1:
			return ParamCommandOutput.prepare(device, params);
		case DeviceTypes.TYPE_IRRIGATION:
			return ParamCommandIrrigation.prepare(device, params);
		case DeviceTypes.TYPE_RECUPERATOR:
			return ParamCommandRecuperator.prepare(device, params);
		case DeviceTypes.TYPE_HEAT_MAIN:
			return ParamCommandHeating.prepare(device, params);
		case DeviceTypes.TYPE_GATE1:
			return ParamCommandOutput.prepare(device, params);
		case DeviceTypes.TYPE_GATE2:
			return ParamCommandOutput.prepare(device, params);
		default:
			return null;
		}
	}

}
