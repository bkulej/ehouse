package pl.eHouse.api.message.params;

import java.util.ArrayList;
import java.util.List;

import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ParamSpliter;

public class ParamEepromSetHeatMain {

	public static List<Integer> prepare(int device, ParamSpliter params)
			throws ConvertException {
		return prepare(device, params.getValueDecAsInt("radiatorTemp") * 10,
				params.getValueDecAsInt("floorTemp") * 10,
				params.getValueDecAsInt("watherTemp") * 10,
				params.getValueAsAddress("floorAddress"),
				params.getValueAsAddress("boilerAddress"));
	}

	public static List<Integer> prepare(int device, int radiatorTemp,
			int floorTemp, int watherTemp,  Address floorAddress, Address boilerAddress) {
		ArrayList<Integer> params = new ArrayList<Integer>();
		params.add(device);
		params.add(radiatorTemp >> 8);
		params.add(radiatorTemp & 0xFF);
		params.add(floorTemp >> 8);
		params.add(floorTemp & 0xFF);
		params.add(watherTemp >> 8);
		params.add(watherTemp & 0xFF);
		params.add(floorAddress.getInt() >> 8);
		params.add(floorAddress.getInt() & 0xFF);
		params.add(boilerAddress.getInt() >> 8);
		params.add(boilerAddress.getInt() & 0xFF);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		params.add(0x00);
		return params;
	}

}
