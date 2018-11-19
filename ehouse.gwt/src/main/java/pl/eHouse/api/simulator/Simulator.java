package pl.eHouse.api.simulator;

import pl.eHouse.api.message.Command;
import pl.eHouse.api.message.MessageInAddress;
import pl.eHouse.api.message.MessageOutAddress;

public class Simulator {

	public static MessageOutAddress run(MessageInAddress messIn) {
		int add = messIn.getAdd().getInt();
		int com = messIn.getCommand().getInt();		
		if(add == 0x1111 || add == 0x2222) {  
			return createResponse(messIn, 0x00, messIn.getDataByteAsInt(0), messIn.getDataByteAsInt(2));
		}
		
		if(add == 0x0160 && com == 0x07) {
			return createResponse(messIn, 0x00, "0200FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		}
		return null;
	}
	
	private static MessageOutAddress createResponse(MessageInAddress messIn, int com, int param1, int param2) {
		Command command = new Command(com);
		MessageOutAddress mess = new MessageOutAddress(messIn.getId(), command, null);
		mess.setAdd(messIn.getAsd());
		mess.setAsd(messIn.getAdd());
		mess.addDataByte(param1);
		mess.addDataByte(param2);
		return mess;
	}
	
	private static MessageOutAddress createResponse(MessageInAddress messIn, int com, String response) {
		Command command = new Command(com);
		MessageOutAddress mess = new MessageOutAddress(messIn.getId(), command, null);
		mess.setAdd(messIn.getAsd());
		mess.setAsd(messIn.getAdd());
		mess.addDataString(response);;
		return mess;
	}

}
