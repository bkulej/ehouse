package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetVolt {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueDecAsInt("lhThreshold"),
				params.getValueDecAsInt("lhDelay"),
				params.getValueAsAddress("lhAddress"),
				params.getValueHexAsInt("lhCommand"),
				params.getValueHexAsInt("lhParam1"),
				params.getValueHexAsInt("lhParam2"),
				params.getValueDecAsInt("hlThreshold"),
				params.getValueDecAsInt("hlDelay"),
				params.getValueAsAddress("hlAddress"),
				params.getValueHexAsInt("hlCommand"),
				params.getValueHexAsInt("hlParam1"),
				params.getValueHexAsInt("hlParam2"));
	}

	public static List<Integer> prepare(int device, int lhThresold,
			int lhDelay, Address lhAddress, int lhCommand, int lhParam1,
			int lhParam2, int hlThresold, int hlDelay, Address hlAddress,
			int hlCommand, int hlParam1, int hlParam2) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(lhThresold);
		params.add(lhDelay);
		params.add(lhAddress.getInt() >> 8);
		params.add(lhAddress.getInt() & 0xFF);
		params.add(lhCommand);
		params.add(lhParam1);
		params.add(lhParam2);
		params.add(hlThresold);
		params.add(hlDelay);
		params.add(hlAddress.getInt() >> 8);
		params.add(hlAddress.getInt() & 0xFF);
		params.add(hlCommand);
		params.add(hlParam1);
		params.add(hlParam2);
		params.add(0x00);
		return params;
	}

}
