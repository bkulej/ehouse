package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetRecuperator {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueDecAsInt("speed1FanIn"),
				params.getValueDecAsInt("speed1FanOut"),
				params.getValueDecAsInt("speed2FanIn"),
				params.getValueDecAsInt("speed2FanOut"),
				params.getValueDecAsInt("speed3FanIn"),
				params.getValueDecAsInt("speed3FanOut"));
	}

	public static List<Integer> prepare(int device, int speed1FanIn, int speed1FanOut,
			int speed2FanIn, int speed2FanOut, int speed3FanIn, int speed3FanOut) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(speed1FanIn);
		params.add(speed1FanOut);
		params.add(speed2FanIn);
		params.add(speed2FanOut);
		params.add(speed3FanIn);
		params.add(speed3FanOut);
		params.add(0x00);
		params.add(0x00);
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
