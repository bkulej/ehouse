package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;

public class ResultCommandVolt {

	public static String prepare(MessageInAddress mess) {
		return "value=" + mess.getDataByteAsInt(0) + ";";
	}

}
