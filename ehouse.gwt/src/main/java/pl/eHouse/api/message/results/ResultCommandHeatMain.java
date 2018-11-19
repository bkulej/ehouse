package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.utils.ConvertUtil;

public class ResultCommandHeatMain {

	public static String prepare(MessageInAddress mess) {
		return "radiatorTempSet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(0))
				+ ";floorTempSet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(4))
				+ ";waterTempSet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(8))
				+ ";heatingTempInp="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(12))
				+ ";waterTempOut="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(16))
				+ ";radiatorStatus=" + mess.getDataByteAsString(20)
				+ ";floorStatus=" + mess.getDataByteAsString(22)
				+ ";waterStatus=" + mess.getDataByteAsString(24) + ";";

	}

}
