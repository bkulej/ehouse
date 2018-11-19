package pl.eHouse.api.message.params;

import java.util.List;

import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEeepromSet {

	public static List<Integer> prepare(int deviceType, int device,
			ParamSpliter params) throws ConvertException {
		switch (deviceType) {
		case DeviceTypes.TYPE_SYSTEM:
			return ParamEepromSetMakro.prepare(device, params);
		case DeviceTypes.TYPE_VOLT:
			return ParamEepromSetVolt.prepare(device, params);
		case DeviceTypes.TYPE_INPUT:
			return ParamEepromSetInput.prepare(device, params);
		case DeviceTypes.TYPE_HEAT_MAIN:
			return ParamEepromSetHeatMain.prepare(device, params);
		case DeviceTypes.TYPE_RECUPERATOR:
			return ParamEepromSetRecuperator.prepare(device, params);
		case DeviceTypes.TYPE_IRRIGATION:
			return ParamEepromSetIrrigation.prepare(device, params);
		default:
			return ParamEepromSetEmpty.prepare();
		}
	}

}
