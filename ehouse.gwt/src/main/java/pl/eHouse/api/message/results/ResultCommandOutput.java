package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;

public class ResultCommandOutput {

	public static String prepare(MessageInAddress mess) {
		return "status=" + mess.getDataByteAsString(0) + ";";
	}

}
