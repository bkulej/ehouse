package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.utils.ConvertUtil;

public class ResultCommandTherm {

	public static String prepare(MessageInAddress mess) {
		return "value="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(0)) + ";";
	}

}
