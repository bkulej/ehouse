package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetHeatMain {

	public static String prepare(ResultSerial result) {
		return "radiatorTemp=" + result.getMessIn().getDataWordAsInt(4) / 10
				+ ";floorTemp=" + result.getMessIn().getDataWordAsInt(8) / 10
				+ ";watherTemp=" + result.getMessIn().getDataWordAsInt(12) / 10
				+ ";floorAddress=" + result.getMessIn().getDataWordAsHexString(16)
				+ ";boilerAddress=" + result.getMessIn().getDataWordAsHexString(20)
				+ ";";
	}

}
