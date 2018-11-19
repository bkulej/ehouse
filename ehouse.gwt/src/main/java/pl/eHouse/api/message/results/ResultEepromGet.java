package pl.eHouse.api.message.results;

import pl.eHouse.api.hardware.DeviceTypes;
import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGet {

	public static int getPage(ResultSerial result) {
		return result.getMessIn().getDataByteAsInt(0);
	}

	public static int getDevice(ResultSerial result) {
		return result.getMessIn().getDataByteAsInt(2);
	}

	public static String getSerial(ResultSerial result) {
		return result.getMessIn().getSerial();
	}

	public static String prepare(int deviceType, ResultSerial result) {
		switch (deviceType) {
		case DeviceTypes.TYPE_SYSTEM:
			return ResultEepromGetMakro.prepare(result);
		case DeviceTypes.TYPE_VOLT:
			return ResultEepromGetVolt.prepare(result);
		case DeviceTypes.TYPE_INPUT:
			return ResultEepromGetInput.prepare(result);
		case DeviceTypes.TYPE_HEAT_MAIN:
			return ResultEepromGetHeatMain.prepare(result);
		case DeviceTypes.TYPE_IRRIGATION:
			return ResultEepromGetIrrigation.prepare(result);
		case DeviceTypes.TYPE_RECUPERATOR:
			return ResultEepromGetRecuperator.prepare(result);
		default:
			return "";
		}
	}

}
