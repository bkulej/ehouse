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
public class MessageCommand {

	/**
	 * Wysłanie request
	 * 
	 * @param serial
	 * @throws CommunicatorException
	 */
	public static Id request(Address address, int command, List<Integer> datas,
			String owner) throws CommunicatorException {
		Id id = Id.generate();
		Command com = new Command(command);
		MessageOutAddress mess = new MessageOutAddress(id, com, owner);
		mess.setAdd(address);
		if (datas != null) {
			for (Integer data : datas) {
				mess.addDataByte(data);
			}
		}
		Communicator.getInstance().sendOut(mess);
		return id;
	}

}
