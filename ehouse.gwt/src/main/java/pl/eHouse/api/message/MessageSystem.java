/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.CommunicatorException;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.ConfigException;
import pl.eHouse.api.hardware.Hardware;
import pl.eHouse.api.hardware.HardwareException;
import pl.eHouse.web.common.client.comet.messages.CometNetworkAllocateMessage;

/**
 * 
 * @author Bartek
 */
public class MessageSystem {

	public static Id allocateRequest(Hardware hardware, String owner)
			throws CommunicatorException {
		if (hardware == null) {
			throw new CommunicatorException("Nie poprawny hardware");
		}
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_NETWORK_ALLOCATE, owner);
		mess.setSerial(hardware.getSerial());
		mess.addDataWord(hardware.getAddress().getInt());
		mess.setTemporary("description", hardware.getDescription());
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public synchronized static ResultSerial allocateResponse(
			MessageOutSerial messOut, MessageInSerial messIn)
			throws HardwareException, ConfigException {
		ResultSerial result = new ResultSerial(messOut, messIn);
		if (!messOut.getTemporary("description").equals(CometNetworkAllocateMessage.MESS_REALLOCATE)) {
			Hardware hardware = new Hardware(result.getSerial(),
					result.getSoftwareType(), result.getHardwareType());
			hardware.setAddress(result.getAddress());
			hardware.setDescription((String) messOut
					.getTemporary("description"));
			Config.getInstance().save(hardware);
			System.out.println("NEW - Zapisa³em alokacje");
		} else {
			System.out.println("NEW - Nie zapisa³em realokacji");
		}
		return result;
	}

	public static Id findRequest(String serial, String owner)
			throws CommunicatorException {
		if (serial == null || serial.length() != 8) {
			throw new CommunicatorException("Nie poprawny serial");
		}
		Id id = Id.generate();
		MessageOutSerial messOut = new MessageOutSerial(id,
				Command.REQUEST_NETWORK_FIND, owner);
		messOut.setRepeat(3, 5000);
		messOut.setSerial(serial);
		Communicator.getInstance().sendOut(messOut);
		return id;
	}

	public synchronized static ResultSerial findResponse(
			MessageOutSerial messOut, MessageInSerial messIn)
			throws HardwareException, CommunicatorException {
		ResultSerial result = new ResultSerial(messOut, messIn);
		MessageOutSerial messEnd = new MessageOutSerial(messIn.getId(),
				Command.RESPONSE_OK, messOut.getOwner());
		messEnd.setSerial(messIn.getSerial());
		Communicator.getInstance().writeOut(messEnd);
		return result;
	}

	public static Id bootRequest(String serial, String owner)
			throws CommunicatorException {
		if (serial == null || serial.length() != 8) {
			throw new CommunicatorException("Nie poprawny serial");
		}
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_BOOT_MODE, owner);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id restartRequest(String serial, String owner)
			throws CommunicatorException {
		if (serial == null || serial.length() != 8) {
			throw new CommunicatorException("Nie poprawny serial");
		}
		Id id = Id.generate();
		MessageOutSerial mess = new MessageOutSerial(id,
				Command.REQUEST_SYSTEM_RESTART, owner);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

}
