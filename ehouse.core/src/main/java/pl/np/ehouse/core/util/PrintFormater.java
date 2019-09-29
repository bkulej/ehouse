package pl.np.ehouse.core.util;

import java.util.List;
import java.util.stream.Collectors;

import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.Types;

/**
 * 
 * @author bkulejewski
 *
 */
public class PrintFormater {

	/**
	 * @param data -
	 * @return -
	 */
	public static String formatByte(int data) {
		try {
			return "0x" + DataConverter.byteToHexString(data);
		} catch (DataConvertException e) {
			return "0xNN";
		}
	}

	/**
	 * @param data -
	 * @return -
	 */
	public static String formatWord(int data) {
		try {
			return "0x" + DataConverter.wordToHexString(data);
		} catch (DataConvertException e) {
			return "0xNNNN";
		}
	}

	/**
	 * @param data -
	 * @return -
	 */
	public static String formatDouble(int data) {
		try {
			return "0x" + DataConverter.doubleToHexString(data);
		} catch (DataConvertException e) {
			return "0xNNNNNNNN";
		}
	}

	/**
	 * @param data -
	 * @return -
	 */
	public static String formatData(List<Integer> data) {
		return data.stream().map(PrintFormater::formatByte).collect(Collectors.joining(","));
	}

	/**
	 * @param data -
	 * @return -
	 */
	public static String formatMessage(Message message) {
		if (message.getType() == Types.SERIAL) {
			return "Message [type=SERIAL serial=" + formatDouble(message.getSerial()) + ", id="
					+ formatByte(message.getId()) + ", command=" + formatByte(message.getCommand()) + ", data=["
					+ formatData(message.getData()) + "]";
		} else if (message.getType() == Types.BOOT) {
			return "Message [type=BOOT serial=" + formatDouble(message.getSerial()) + ", id="
					+ formatByte(message.getId()) + ", command=" + formatByte(message.getCommand()) + ", data=["
					+ formatData(message.getData()) + "]";
		} else if (message.getType() == Types.ADDRESS) {
			return "Message [type=ADDRESS, asd=" + formatWord(message.getAsd()) + ", add="
					+ formatWord(message.getAdd()) + ", id=" + formatByte(message.getId()) + ", command="
					+ formatByte(message.getCommand()) + ", data=[" + formatData(message.getData()) + "]";
		} else {
			return "Message [type=" + formatByte(message.getType()) + ", serial=" + formatDouble(message.getSerial())
					+ ", asd" + formatWord(message.getAsd()) + ", add=" + formatWord(message.getAdd()) + ", id="
					+ formatByte(message.getId()) + ", command=" + formatByte(message.getCommand()) + ", data=["
					+ formatData(message.getData()) + "]";
		}
	}

}
