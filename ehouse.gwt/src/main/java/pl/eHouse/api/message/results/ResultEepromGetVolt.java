package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetVolt extends ResultEepromGet {

	public static String prepare(ResultSerial result) {
		return "lhThreshold=" + result.getMessIn().getDataByteAsIntString(4)
				+ ";lhDelay=" + result.getMessIn().getDataByteAsIntString(6)
				+ ";lhAddress=" + result.getMessIn().getDataWordAsHexString(8)
				+ ";lhCommand=" + result.getMessIn().getDataByteAsHexString(12)
				+ ";lhParam1=" + result.getMessIn().getDataByteAsHexString(14)
				+ ";lhParam2=" + result.getMessIn().getDataByteAsHexString(16)
				+ ";hlThreshold=" + result.getMessIn().getDataByteAsIntString(18)
				+ ";hlDelay=" + result.getMessIn().getDataByteAsIntString(20)
				+ ";hlAddress=" + result.getMessIn().getDataWordAsHexString(22)
				+ ";hlCommand=" + result.getMessIn().getDataByteAsHexString(26)
				+ ";hlParam1=" + result.getMessIn().getDataByteAsHexString(28)
				+ ";hlParam2=" + result.getMessIn().getDataByteAsHexString(30)
				+ ";";

	}

}
