package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetIrrigation {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueDecAsInt("section1"),
				params.getValueDecAsInt("section2"),
				params.getValueDecAsInt("section3"),
				params.getValueDecAsInt("section4"),
				params.getValueDecAsInt("section5"),
				params.getValueDecAsInt("section6"),
				params.getValueDecAsInt("section7"),
				params.getValueDecAsInt("section8"));
	}

	public static List<Integer> prepare(int device, int section1, int section2,
			int section3, int section4, int section5, int section6,
			int section7, int section8) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(section1);
		params.add(section2);
		params.add(section3);
		params.add(section4);
		params.add(section5);
		params.add(section6);
		params.add(section7);
		params.add(section8);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		return params;
	}

}
