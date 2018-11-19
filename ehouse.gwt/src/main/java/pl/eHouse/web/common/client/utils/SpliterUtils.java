package pl.eHouse.web.common.client.utils;

public class SpliterUtils {

	/**
	 * 
	 * @param values key=value;key=value;key=value
	 * @param key
	 * @return
	 */
	public static String valueSplit(String values, String key) {
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
	
	/**
	 * 
	 * @param values "key{value}key{value}key{value}"
	 * @param key
	 * @return
	 */
	public static String mapperSplit(String values, String key) {
		int start = values.indexOf(key);
		if (start < 0) {
			return null;
		}
		int middle = values.indexOf('{', start);
		if (middle < 0) {
			return null;
		}
		int stop = values.indexOf('}', middle);
		if (stop < 0) {
			return null;
		}
		return values.substring(middle + 1, stop);
	}
}
