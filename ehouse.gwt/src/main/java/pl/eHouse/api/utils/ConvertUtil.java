/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Bartek
 */
public class ConvertUtil {
	
	private final static SimpleDateFormat format =  new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");

	/**
	 * Konwersja z szesnastkowego ciagu znakow do liczby
	 * 
	 * @param value
	 * @param message
	 * @return
	 */
	public static int hexToInt(String value, String message)
			throws ConvertException {
		try {
			return Integer.parseInt(value, 16);
		} catch (NumberFormatException ex) {
			throw new ConvertException(message);
		} catch (NullPointerException ex) {
			throw new ConvertException(message);
		}
	}

	/**
	 * Konwersja z szesnastkowego ciagu znakow do liczby
	 * 
	 * @param value
	 * @return
	 * @throws ConvertException
	 */
	public static int hexToInt(String value) throws ConvertException {
		try {
			return Integer.parseInt(value, 16);
		} catch (NumberFormatException ex) {
			throw new ConvertException(value);
		} catch (NullPointerException ex) {
			throw new ConvertException(value);
		}
	}

	/**
	 * Konwersja z szesnastkowego znaku do cyfry
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
	 * Konwersja z szesnastkowego ciagu znakow do liczby
	 * 
	 * @param value
	 * @return
	 * @throws ConvertException
	 */
	public static char hexToChar(String value) throws ConvertException {
		try {
			return (char)Integer.parseInt(value, 16);
		} catch (NumberFormatException ex) {
			throw new ConvertException(value);
		} catch (NullPointerException ex) {
			throw new ConvertException(value);
		}
	}

	/**
	 * Konwersja z dziesietnego ciagu znakow do liczby
	 * 
	 * @param value
	 * @param message
	 * @return
	 */
	public static int decToInt(String value, String message)
			throws ConvertException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			throw new ConvertException(message);
		} catch (NullPointerException ex) {
			throw new ConvertException(message);
		}
	}

	/**
	 * Konwersja z dziesietnego ciagu znakow do liczby
	 * 
	 * @param value
	 * @return
	 * @throws ConvertException
	 */
	public static int decToInt(String value) throws ConvertException {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			throw new ConvertException(value);
		} catch (NullPointerException ex) {
			throw new ConvertException(value);
		}
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

	public static String wordToStringTemp(int data) {
		float temp = data > Short.MAX_VALUE ? -(65535 - data + 1) : data;
		String value = Float.toString(temp / 10);
		return value;
	}
	
	public static String getStringDate() {
		 return format.format(new Date());
	}

}
