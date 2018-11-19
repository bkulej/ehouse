package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.utils.ConvertUtil;

public class ResultCommandHeat3Way {

	public static String prepare(MessageInAddress mess) {
		return "heatTempSet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(0))
				+ ";valveTempOut="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(4))
				+ ";valveTempRet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(8))
				+ ";pumpStatus=" + mess.getDataByteAsString(12)
				+ ";valveStatus=" + mess.getDataByteAsString(14) + ";";
	}

}
