package pl.eHouse.web.common.server.execute;

import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.Communicator;
import pl.eHouse.api.message.Address;
import pl.eHouse.api.message.Command;
import pl.eHouse.api.message.Id;
import pl.eHouse.api.message.MessageOutAddress;
import pl.eHouse.web.common.client.comet.messages.CometErrorResponse;
import pl.eHouse.web.common.client.comet.messages.CometSendMessage;
import pl.eHouse.web.common.server.utils.GwtOwner;

public class SendMessageExecutor {

	public static void execute(CometSendMessage message,
			GwtAtmosphereResource resource) {
		try {
			Id id = Id.generate();
			Command com = new Command(message.getCommand());
			MessageOutAddress mess = new MessageOutAddress(id, com,
					GwtOwner.get(resource));
			mess.setAdd(new Address(message.getAddress()));
			mess.addDataString(message.getData());
			Communicator.getInstance().writeOut(mess);
		} catch (Exception e) {
			resource.post(new CometErrorResponse(e.getMessage()));
			e.printStackTrace();
		}
	}

}