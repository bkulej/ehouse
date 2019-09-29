package pl.np.ehouse.core.util;

import java.util.List;

/**
 * @author Bartek
 */
public class DataConverter {

	/**
	 * @param ascii -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static int hexAsciiToDigit(int ascii) throws DataConvertException {
		if (ascii >= '0' && ascii <= '9') {
			return ascii - '0';
		} else if (ascii >= 'A' && ascii <= 'F') {
			return ascii - 'A' + 10;
		} else if (ascii >= 'a' && ascii <= 'f') {
			return ascii - 'a' + 10;
		} else {
			throw new DataConvertException("Incorrect ascii sign");
		}
	}

	/**
	 * @param value -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static int digitToHexAscii(int value) throws DataConvertException {
		if (value >= 0 && value <= 9) {
			return '0' + value;
		} else if (value >= 10 && value <= 15) {
			return 'A' + value - 10;
		} else {
			throw new DataConvertException("Incorrect hex digit");
		}
	}

	/**
	 * @param data -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static String byteToHexString(int data) throws DataConvertException {
		return integerToHexString(data, 2);
	}

	/**
	 * @param data -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static String wordToHexString(int data) throws DataConvertException {
		return integerToHexString(data, 4);
	}

	/**
	 * @param data -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static String doubleToHexString(int data) throws DataConvertException {
		return integerToHexString(data, 8);
	}

	/*
	 *
	 */
	private static String integerToHexString(int data, int position) throws DataConvertException {
		StringBuilder result = new StringBuilder();
		result.append(Integer.toHexString(data).toUpperCase());
		if (result.length() > position) {
			throw new DataConvertException("Number too large");
		}
		while (result.length() < position) {
			result.insert(0, "0");
		}
		return result.toString();
	}

	/**
	 * @param data     -
	 * @param position -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static int getDoubleFromHexAsciiList(List<Integer> data, int position) throws DataConvertException {
		try {
			return getWordFromHexAsciiList(data, position) << 16 | getWordFromHexAsciiList(data, position + 4);
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * @param data     -
	 * @param position -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static int getWordFromHexAsciiList(List<Integer> data, int position) throws DataConvertException {
		try {
			return getByteFromHexAsciiList(data, position) << 8 | getByteFromHexAsciiList(data, position + 2);
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * @param data     -
	 * @param position -
	 * @return -
	 * @throws DataConvertException -
	 */
	public static int getByteFromHexAsciiList(List<Integer> data, int position) throws DataConvertException {
		try {
			int value = hexAsciiToDigit(data.get(position)) << 4;
			value |= hexAsciiToDigit(data.get(position + 1));
			return value;
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * @param data  -
	 * @param value -
	 * @throws DataConvertException -
	 */
	public static void addByteToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
		data.add(digitToHexAscii((value >> 4) & 0xFF));
		data.add(digitToHexAscii(value & 0x0F));
	}

	/**
	 * @param data  -
	 * @param value -
	 * @throws DataConvertException -
	 */
	public static void addWordToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
		addByteToHexAsciiList(data, (value >> 8) & 0xFF);
		addByteToHexAsciiList(data, value & 0xFF);
	}

	/**
	 * @param data  -
	 * @param value -
	 * @throws DataConvertException -
	 */
	public static void addDoubleToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
		addWordToHexAsciiList(data, (value >> 16) & 0xFFFF);
		addWordToHexAsciiList(data, value & 0xFFFF);
	}
}
