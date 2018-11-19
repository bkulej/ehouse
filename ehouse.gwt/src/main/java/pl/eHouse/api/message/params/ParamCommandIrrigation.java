package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamCommandIrrigation {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(params.getValueHexAsInt("status"));
	}

	public static List<Integer> prepare(Integer status) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		if (status != null) {
			params.add(status);
		}
		return params;
	}

}
