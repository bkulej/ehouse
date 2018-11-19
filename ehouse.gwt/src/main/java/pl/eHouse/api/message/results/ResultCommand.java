package pl.eHouse.api.message.results;

import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.message.ResultAddress;

public class ResultCommand {

	public static String prepare(int deviceType, ResultAddress result) {
		switch (deviceType) {
		case DeviceTypes.TYPE_OUTPUT1:
			return ResultCommandOutput.prepare(result.getMessIn());
		case DeviceTypes.TYPE_THERM:
			return ResultCommandTherm.prepare(result.getMessIn());
		case DeviceTypes.TYPE_VOLT:
			return ResultCommandVolt.prepare(result.getMessIn());
		case DeviceTypes.TYPE_HEAT_MAIN:
			return ResultCommandHeatMain.prepare(result.getMessIn());
		case DeviceTypes.TYPE_HEAT_3WAY:
			return ResultCommandHeat3Way.prepare(result.getMessIn());
		case DeviceTypes.TYPE_HEAT_4WAY:
			return ResultCommandHeat4Way.prepare(result.getMessIn());
		case DeviceTypes.TYPE_IRRIGATION:
			return ResultCommandIrrigation.prepare(result.getMessIn());
		case DeviceTypes.TYPE_RECUPERATOR:
			return ResultCommandRecuperator.prepare(result.getMessIn());
		case DeviceTypes.TYPE_GATE1:
			return ResultCommandOutput.prepare(result.getMessIn());
		case DeviceTypes.TYPE_GATE2:
			return ResultCommandOutput.prepare(result.getMessIn());
		default:
			return "";
		}
	}

}
