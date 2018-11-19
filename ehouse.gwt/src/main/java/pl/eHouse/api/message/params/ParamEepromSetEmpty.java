package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

public class ParamEepromSetEmpty {

	public static List<Integer> prepare() {
		ArrayList<Integer> params = new ArrayList<Integer>();
		for (int i = 0; i < 0xF; i++) {
			params.add(0xFF);
		}
		return params;
	}

}
