/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api;

import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.HardwareException;
import pl.eHouse.api.message.Command;
import pl.eHouse.api.message.MessageInSerial;
import pl.eHouse.api.message.MessageSystem;
import pl.eHouse.api.message.MessageOutSerial;
import pl.eHouse.api.message.ResultSerial;

/**
 * 
 * @author Bartek
 */
public class Decoder {

	/**
	 * Dekodowanie wiadomosci
	 * 
	 * @throws CommunicatorException
	 */
	public static ResultSerial decode(MessageOutSerial messOut,
			MessageInSerial messIn) throws HardwareException, ConfigException,
			CommunicatorException {
		ResultSerial result = null;
		if (messOut.getCommand().equals(Command.REQUEST_NETWORK_FIND)) {
			result = MessageSystem.findResponse(messOut, messIn);
		} else if (messOut.getCommand()
				.equals(Command.REQUEST_NETWORK_ALLOCATE)) {
			result = MessageSystem.allocateResponse(messOut, messIn);
		} else {
			result = new ResultSerial(messOut, messIn);
		}
		return result;
	}

}
