package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.utils.ConvertUtil;

public class ResultCommandRecuperator {

	public static String prepare(MessageInAddress mess) {
		return "speedStatus=" + mess.getDataByteAsString(0) + ";bypassStatus="
				+ mess.getDataByteAsString(2) + ";gwcStatus="
				+ mess.getDataByteAsString(4) + ";speedFanIn="
				+ mess.getDataWordAsInt(6) + ";speedFanOut="
				+ mess.getDataWordAsInt(10) + ";tempRecIn="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(14))
				+ ";tempRoomIn="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(18))
				+ ";tempRoomOut="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(22))
				+ ";tempRecOut="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(26))
				+ ";tempGwc="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(30)) + ";";
	}

}
