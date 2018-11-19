package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetInput {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueDecAsInt("delay"),
				params.getValueAsAddress("addressAction1"),
				params.getValueHexAsInt("commandAction1"),
				params.getValueHexAsInt("param1Action1"),
				params.getValueHexAsInt("param2Action1"),
				params.getValueAsAddress("addressAction2"),
				params.getValueHexAsInt("commandAction2"),
				params.getValueHexAsInt("param1Action2"),
				params.getValueHexAsInt("param2Action2"));
	}

	public static List<Integer> prepare(int device, int delay,
			Address addressAction1, int commandAction1, int param1Action1, int param2Action1,
			Address addressAction2, int commandAction2, int param1Action2, int param2Action2) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(delay >> 8);
		params.add(delay & 0xFF);
		params.add(addressAction1.getInt() >> 8);
		params.add(addressAction1.getInt() & 0xFF);
		params.add(commandAction1);
		params.add(param1Action1);
		params.add(param2Action1);
		params.add(addressAction2.getInt() >> 8);
		params.add(addressAction2.getInt() & 0xFF);
		params.add(commandAction2);
		params.add(param1Action2);
		params.add(param2Action2);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		return params;
	}

}
