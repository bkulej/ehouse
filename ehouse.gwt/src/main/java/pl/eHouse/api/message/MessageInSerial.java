/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.logging.Level;
import java.util.logging.Logger;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 * 
 * @author Bartek
 */
public class MessageInSerial {

	private Id id;
	private Command command;
	private String serial;
	private String data;
	private int byteCounter;

	/**
	 * Konstruktor
	 */
	public MessageInSerial(MessageIn mess) {
		id = new Id();
		command = new Command();
		serial = "";
		data = "";
		byteCounter = 0;
		for (Integer part : mess.getParts()) {
			resolvePart((char) part.intValue());
		}
	}

	public Command getCommand() {
		return command;
	}

	public Id getId() {
		return id;
	}

	public String getSerial() {
		return serial;
	}

	public String getData() {
		return data;
	}

	public int getDataWordAsInt(int position) {
		try {
			return ConvertUtil.hexToInt(data.substring(position, position + 4),
					"Conversion error");
		} catch (ConvertException ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return 0;
		}
	}

	public Address getDataWordAsAddress(int position) {
		try {
			return new Address(data.substring(position, position + 4));
		} catch (ConvertException ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return null;
		}
	}

	public String getDataWordAsHexString(int position) {
		return data.substring(position, position + 4);
	}

	public String getDataWordAsIntString(int position) {
		return Integer.toString(getDataWordAsInt(position));
	}

	public int getDataByteAsInt(int position) {
		try {
			return ConvertUtil.hexToInt(data.substring(position, position + 2),
					"Conversion error");
		} catch (Exception ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return 0;
		}
	}

	public String getDataByteAsHexString(int position) {
		return data.substring(position, position + 2);
	}

	public String getDataByteAsIntString(int position) {
		return Integer.toString(getDataByteAsInt(position));
	}

	public String getDataAsString(int position, int length) {
		try {
			String result = "";
			for (int i = position; i < (position + length * 2); i = i + 2) {
				result += ConvertUtil.hexToChar(data.substring(i, i + 2));
			}
			return result;
		} catch (Exception ex) {
			Logger.getLogger(MessageInSerial.class.getName()).log(Level.SEVERE,
					null, ex);
			return "";
		}
	}

	public String getKey() {
		return id + "-SERIAL";
	}

	private void resolvePart(char part) {
		// Dodanie czesci
		switch (++byteCounter) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			serial += part;
			break;
		case 9:
			id.setDigit(part, 1);
			break;
		case 10:
			id.setDigit(part, 0);
			break;
		case 11:
			command.setDigit(part, 1);
			break;
		case 12:
			command.setDigit(part, 0);
			break;
		default:
			data += part;
		}
	}

	@Override
	public String toString() {
		return "<IS>{serial=" + serial + ",id=" + id + ",command=" + command
				+ ",data=" + data + "}";
	}

}
