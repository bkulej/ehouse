package pl.eHouse.web.common.client.utils;

import pl.eHouse.web.common.client.locale.LocaleFactory;

public class Validator {

	public static String checkSerial(String serial) throws ValidatorException {
		if ((serial == null) || (serial.length() != 8)) {
			throw new ValidatorException(LocaleFactory.getMessages()
					.Validator_badSerial());
		}
		return serial;
	}

	public static String checkString(String string, String message)
			throws ValidatorException {
		if ((string == null) || (string.length() == 0)) {
			throw new ValidatorException(message);
		}
		return string;
	}

	public static String checkByteHex(String string, String message)
			throws ValidatorException {
		if ((string.length() == 0) || (string.length() > 2)) {
			throw new ValidatorException(message);
		}
		try {
			Integer.parseInt(string, 16);
			return string;
		} catch (Exception e) {
			throw new ValidatorException(message);
		}
	}

	public static String checkWordHex(String string, String message)
			throws ValidatorException {
		if ((string.length() == 0) || (string.length() > 4)) {
			throw new ValidatorException(message);
		}
		try {
			Integer.parseInt(string, 16);
			return string;
		} catch (Exception e) {
			throw new ValidatorException(message);
		}
	}
	
	public static String checkDataHex(String string, String message)
			throws ValidatorException {
		if(string.length() == 0) {
			return "";
		}
		try {
			Integer.parseInt(string, 16);
			return string;
		} catch (Exception e) {
			throw new ValidatorException(message);
		}
	}

	public static String checkDecWord(String string, String message)
			throws ValidatorException {
		if ((string == null) || (string.length() == 0)) {
			throw new ValidatorException(message);
		}
		try {
			int value = Integer.parseInt(string); 
			if(value>65535 || value<0) {
				throw new ValidatorException(message);
			}
			return string;
		} catch (Exception e) {
			throw new ValidatorException(message);
		}
	}
	
	public static String checkDecByte(String string, String message)
			throws ValidatorException {
		if ((string == null) || (string.length() == 0)) {
			throw new ValidatorException(message);
		}
		try {
			int value = Integer.parseInt(string); 
			if(value>255 || value<0) {
				throw new ValidatorException(message);
			}
			return string;
		} catch (Exception e) {
			throw new ValidatorException(message);
		}
	}

}
