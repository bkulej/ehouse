package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetMakro {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueHexAsInt("makro"),
				params.getValueHexAsInt("dev1Number"),
				params.getValueHexAsInt("dev1Command"),
				params.getValueHexAsInt("dev1Param1"),
				params.getValueHexAsInt("dev1Param2"),
				params.getValueHexAsInt("dev2Number"),
				params.getValueHexAsInt("dev2Command"),
				params.getValueHexAsInt("dev2Param1"),
				params.getValueHexAsInt("dev2Param2"),
				params.getValueHexAsInt("dev3Number"),
				params.getValueHexAsInt("dev3Command"),
				params.getValueHexAsInt("dev3Param1"),
				params.getValueHexAsInt("dev3Param2"));
	}

	public static List<Integer> prepare(int device, int makro, int dev1Number,
			int dev1Command, int dev1Param1, int dev1Param2, int dev2Number,
			int dev2Command, int dev2Param1, int dev2Param2, int dev3Number,
			int dev3Command, int dev3Param1, int dev3Param2) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(makro >> 8);
		params.add(makro & 0xFF);
		params.add(dev1Number);
		params.add(dev1Command);
		params.add(dev1Param1);
		params.add(dev1Param2);
		params.add(dev2Number);
		params.add(dev2Command);
		params.add(dev2Param1);
		params.add(dev2Param2);
		params.add(dev3Number);
		params.add(dev3Command);
		params.add(dev3Param1);
		params.add(dev3Param2);
		params.add(0x00);
		return params;
	}

}
