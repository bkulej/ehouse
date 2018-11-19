package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetIrrigation {

	public static String prepare(ResultSerial result) {
		return "section1=" + result.getMessIn().getDataByteAsIntString(4)
				+ ";section2=" + result.getMessIn().getDataByteAsIntString(6)
				+ ";section3=" + result.getMessIn().getDataByteAsIntString(8)
				+ ";section4=" + result.getMessIn().getDataByteAsIntString(10)
				+ ";section5=" + result.getMessIn().getDataByteAsIntString(12)
				+ ";section6=" + result.getMessIn().getDataByteAsIntString(14)
				+ ";section7=" + result.getMessIn().getDataByteAsIntString(16)
				+ ";section8=" + result.getMessIn().getDataByteAsIntString(18)
				+ ";";
	}

}
