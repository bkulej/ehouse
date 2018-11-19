package pl.eHouse.api.utils;

import pl.eHouse.api.message.Address;

public class ParamSpliter {

	private String values;

	public ParamSpliter(String values) {
		super();
		this.values = values;
	}

	public String getValueAsString(String key) {
		int start = values.indexOf(key);
		if (start < 0) {
			return null;
		}
		int middle = values.indexOf('=', start);
		if (middle < 0) {
			return null;
		}
		int stop = values.indexOf(';', middle);
		if (stop < 0) {
			return null;
		}
		return values.substring(middle + 1, stop);
	}

	public Integer getValueHexAsInt(String key) throws ConvertException {
		String temp = getValueAsString(key);
		return temp != null ? ConvertUtil.hexToInt(temp) : null;
	}

	public Integer getValueDecAsInt(String key) throws ConvertException {
		String temp = getValueAsString(key);
		return temp != null ? ConvertUtil.decToInt(temp) : null;
	}

	public Address getValueAsAddress(String key) throws ConvertException {
		String temp = getValueAsString(key);
		return temp != null ? new Address(temp) : null;
	}

	@Override
	public String toString() {
		return "ParamSpliter [values=" + values + "]";
	}

}
