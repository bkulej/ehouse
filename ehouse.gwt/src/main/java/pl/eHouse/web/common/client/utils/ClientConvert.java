/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.web.common.client.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * 
 * @author Bartek
 */
public class ClientConvert {

	/**
	 * Konwersja z szesnastkowego ciągu znakow do liczby
	 * 
	 * @param value
	 * @param message
	 * @return
	 */
	public static int hexToInt(String value) {
		return Integer.parseInt(value, 16);
	}

	/**
	 * Konwersja z szesnastkowego ciguu znakow do liczby
	 * 
	 * @param value
	 * @param shitf
	 * @return
	 */
	public static int hexToInt(char value, int shift) {
		try {
			return Character.digit(value, 16) << shift;
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * Konwersja z szesnastkowego ciągu znakow do liczby
	 * 
	 * @param value
	 * @param message
	 * @return
	 */
	public static int decToInt(String value) {

		return Integer.parseInt(value);

	}

	/**
	 * Bajt do 2 znakow
	 * 
	 * @param data
	 * @return
	 */
	public static String byteToHex(int data) {
		return intToHex(data, 2);
	}

	/**
	 * Słowo do 4 znakow
	 * 
	 * @param data
	 * @return
	 */
	public static String wordToHex(int data) {
		return intToHex(data, 4);
	}

	/**
	 * Konwersja liczby na szesnastkową na określonej liczbie pozycji
	 * 
	 * @param data
	 * @param pos
	 * @return
	 */
	public static String intToHex(int data, int pos) {
		String value = Integer.toHexString(data);
		while (value.length() < pos) {
			value = "0" + value;
		}
		return value.toUpperCase();
	}

	/**
	 * Konwersja liczby na kolejna cyfre szesnastkowa
	 * 
	 * @param data
	 * @param pos
	 * @return
	 */
	public static char intToDigit(int data, int digit) {
		int value = (data >> (digit * 4)) & 0xf;
		return value > 9 ? (char) (value + 55) : (char) (value + 48);
	}

	/**
	 * Konwersja liczby na binarna na określonej liczbie pozycji
	 * 
	 * @param data
	 * @param pos
	 * @return
	 */
	public static String toBinString(int data, int pos) {
		String value = Integer.toBinaryString(data);
		while (value.length() < pos) {
			value = "0" + value;
		}
		return value;
	}

	public static String arrayToString(String[] datas) {
		boolean first = true;
		String tempDatas = "";
		for (String data : datas) {
			if (first) {
				tempDatas = data;
				first = false;
			} else {
				tempDatas += ClientConst.CONFIG_DATA_DELIMITER + data;
			}
		}
		return tempDatas;
	}

	public static String[] stringToArray(String data) {
		return data.split(ClientConst.CONFIG_DATA_DELIMITER);
	}

	public static String dateToString(Date date) {
		return DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
	}

}
