/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.message;

import java.util.List;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.CommunicatorException;
import pl.eHouse.api.bootloader.IntelHexException;
import pl.eHouse.api.utils.ConvertException;

/**
 * 
 * @author Bartek
 */
public class MessageBoot {

	// Numery komunikatow
	public final static Command OK_RESPONSE = new Command(0x10);
	public final static Command FIND_REQUEST = new Command(0x11);
	public final static Command BEGIN_REQUEST = new Command(0x12);
	public final static Command SAVE_REQUEST = new Command(0x13);
	public final static Command END_REQUEST = new Command(0x14);
	public final static Command START_REQUEST = new Command(0x15);
	public final static Command ERROR_RESPONSE = new Command(0x1F);

	public static Id findRequest(String serial, String owner)
			throws CommunicatorException, ConvertException {
		Id id = Id.generate();
		MessageOutBoot mess = new MessageOutBoot(id, FIND_REQUEST, owner);
		mess.setRepeat(3, 1000);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static ResultBoot findResponse(MessageOutBoot messOut,
			MessageInBoot messIn) {
		return new ResultBoot(messOut, messIn);
	}

	public static Id beginRequest(String serial, String owner)
			throws CommunicatorException, ConvertException {
		Id id = Id.generate();
		MessageOutBoot mess = new MessageOutBoot(id, BEGIN_REQUEST, owner);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id saveRequest(String serial, int address,
			List<Integer> datas, String owner) throws CommunicatorException,
			ConvertException, IntelHexException {
		Id id = Id.generate();
		MessageOutBoot mess = new MessageOutBoot(id, SAVE_REQUEST, owner);
		mess.setRepeat(5, 2000);
		mess.setSerial(serial);
		mess.addDataWord(address);
		for (Integer data : datas) {
			mess.addDataByte(data);
		}
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id endRequest(String serial, String owner)
			throws CommunicatorException, ConvertException {
		Id id = Id.generate();
		MessageOutBoot mess = new MessageOutBoot(id, END_REQUEST, owner);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

	public static Id startRequest(String serial, String owner)
			throws CommunicatorException, ConvertException {
		Id id = Id.generate();
		MessageOutBoot mess = new MessageOutBoot(id, START_REQUEST, owner);
		mess.setSerial(serial);
		Communicator.getInstance().sendOut(mess);
		return id;
	}

}
