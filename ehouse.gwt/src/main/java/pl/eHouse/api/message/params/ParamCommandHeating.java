package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamCommandHeating {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(params.getValueDecAsInt("temperature"));
	}

	public static List<Integer> prepare(Integer temperature) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		if (temperature != null) {
			int temp = temperature * 10;
			params.add(temp >> 8 );
			params.add(temp & 0xFF);
		}
		return params;
	}

}
