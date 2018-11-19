package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetRecuperator {

	public static String prepare(ResultSerial result) {
		return "speed1FanIn=" + result.getMessIn().getDataByteAsIntString(4)
				+ ";speed1FanOut=" + result.getMessIn().getDataByteAsIntString(6)
				+ ";speed2FanIn=" + result.getMessIn().getDataByteAsIntString(8)
				+ ";speed2FanOut=" + result.getMessIn().getDataByteAsIntString(10)
				+ ";speed3FanIn=" + result.getMessIn().getDataByteAsIntString(12)
				+ ";speed3FanOut=" + result.getMessIn().getDataByteAsIntString(14)
				+ ";";
	}

}
