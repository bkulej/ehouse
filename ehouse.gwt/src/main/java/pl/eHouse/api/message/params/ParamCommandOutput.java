package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamCommandOutput {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(params.getValueHexAsInt("status"),
				params.getValueHexAsInt("delay"));
	}

	public static List<Integer> prepare(Integer status, Integer delay) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		if (status != null) {
			params.add(status);
		}
		if (delay != null) {
			params.add(delay);
		}
		return params;
	}

}
