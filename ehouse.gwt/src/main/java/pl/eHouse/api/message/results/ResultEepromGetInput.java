package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetInput {

	public static String prepare(ResultSerial result) {
		return "delay=" + result.getMessIn().getDataWordAsIntString(4)
				+ ";addressAction1=" + result.getMessIn().getDataWordAsHexString(8)
				+ ";commandAction1=" + result.getMessIn().getDataByteAsHexString(12)
				+ ";param1Action1=" + result.getMessIn().getDataByteAsHexString(14)
				+ ";param2Action1=" + result.getMessIn().getDataByteAsHexString(16)
				+ ";addressAction2=" + result.getMessIn().getDataWordAsHexString(18)
				+ ";commandAction2=" + result.getMessIn().getDataByteAsHexString(22)
				+ ";param1Action2=" + result.getMessIn().getDataByteAsHexString(24)
				+ ";param2Action2=" + result.getMessIn().getDataByteAsHexString(26)
				+ ";";
	}

}
