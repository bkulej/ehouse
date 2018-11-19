/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import java.util.List;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.CommunicatorException;

/**
 * 
 * @author Bartek
 */
public class MessageEeprom {

	public static Id statusRequest(String serial, String owner)
			throws CommunicatorException {
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_EEPROM_STATUS, owner);
		mess.setSerial(serial);
		mess.setRepeat(5, 1000);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id getRequest(String serial, int page, String owner)
			throws CommunicatorException {
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_EEPROM_GET, owner);
		mess.setSerial(serial);
		mess.addDataByte(page);
		mess.setRepeat(5, 1000);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id setRequest(String serial, int page, List<Integer> datas,
			String owner) throws CommunicatorException {
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_EEPROM_SET, owner);
		mess.setSerial(serial);
		mess.addDataByte(page);
		for (Integer data : datas) {
			mess.addDataByte(data);
		}
		mess.setRepeat(5, 1000);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

}
