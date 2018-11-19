package pl.eHouse.api.message.results;

import pl.eHouse.api.message.ResultSerial;

public class ResultEepromGetMakro {

	public static String prepare(ResultSerial result) {
		return "makro=" + result.getMessIn().getDataWordAsHexString(4)
				+ ";dev1Number=" + result.getMessIn().getDataByteAsHexString(8)
				+ ";dev1Command=" + result.getMessIn().getDataByteAsHexString(10)
				+ ";dev1Param1=" + result.getMessIn().getDataByteAsHexString(12)
				+ ";dev1Param2=" + result.getMessIn().getDataByteAsHexString(14)
				+ ";dev2Number=" + result.getMessIn().getDataByteAsHexString(16)
				+ ";dev2Command=" + result.getMessIn().getDataByteAsHexString(18)
				+ ";dev2Param1=" + result.getMessIn().getDataByteAsHexString(20)
				+ ";dev2Param2=" + result.getMessIn().getDataByteAsHexString(22)
				+ ";dev3Number=" + result.getMessIn().getDataByteAsHexString(24)
				+ ";dev3Command=" + result.getMessIn().getDataByteAsHexString(26)
				+ ";dev3Param1=" + result.getMessIn().getDataByteAsHexString(28)
				+ ";dev3Param2=" + result.getMessIn().getDataByteAsHexString(30)
				+ ";";
	}

}
