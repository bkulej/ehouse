package pl.np.ehouse.core.utils;

import java.util.List;

/**
 * 
 * @author Bartek
 *
 */
public class DataConverter {

	/**
	 * 
	 * @param data
	 * @param position
	 * @return
	 * @throws DataConvertException
	 */
	public static int getByte(List<Integer> data, int position) throws DataConvertException {
		try {
			int value = getDigitFromHexAscii(data.get(position)) << 4;
			value |= getDigitFromHexAscii(data.get(position + 1));
			return value;
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * 
	 * @param data
	 * @param position
	 * @return
	 * @throws DataConvertException
	 */
	public static int getWord(List<Integer> data, int position) throws DataConvertException {
		try {
			int value = getByte(data, position) << 8;
			value |= getByte(data, position + 2);
			return value;
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * 
	 * @param data
	 * @param position
	 * @return
	 * @throws DataConvertException
	 */
	public static long getDouble(List<Integer> data, int position) throws DataConvertException {
		try {
			long value = ((long) getWord(data, position)) << 16;
			value |= getWord(data, position + 4);
			return value;
		} catch (IndexOutOfBoundsException e) {
			throw new DataConvertException("Data index out of bounds");
		}
	}

	/**
	 * 
	 * @param ascii
	 * @return
	 * @throws DataConvertException
	 */
	public static int getDigitFromHexAscii(int ascii) throws DataConvertException {
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

}
