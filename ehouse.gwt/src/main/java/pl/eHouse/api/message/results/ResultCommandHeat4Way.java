package pl.eHouse.api.message.results;

import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.utils.ConvertUtil;

public class ResultCommandHeat4Way {

	public static String prepare(MessageInAddress mess) {
		return "heatTempSet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(0))
				+ ";boilerTempInp="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(4))
				+ ";valveTempOut="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(8))
				+ ";valveTempRet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(12))
				+ ";boilerTemprRet="
				+ ConvertUtil.wordToStringTemp(mess.getDataWordAsInt(16))
				+ ";boilerStatus=" + mess.getDataByteAsString(20)
				+ ";valveStatus=" + mess.getDataByteAsString(22) + ";";
	}

}
