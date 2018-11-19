package pl.eHouse.web.common.client.command;

import java.util.ArrayList;

import pl.eHouse.web.common.client.comet.CometInterface;
import pl.eHouse.web.common.client.comet.CometMessage;
import pl.eHouse.web.common.client.comet.messages.CometCommandResponse;

public class CommandReceiver implements CometInterface {

	private static CommandReceiver cometReceiver;
	private ArrayList<ReceiveMapper> receivers;

	public CommandReceiver() {
		receivers = new ArrayList<ReceiveMapper>();
	}

	public synchronized static CommandReceiver getReceiver() {
		if (cometReceiver == null) {
			cometReceiver = new CommandReceiver();
		}
		return cometReceiver;
	}

	public synchronized static void addReceiver(ReceiveMapper receiver) {
		if (cometReceiver == null) {
			cometReceiver = new CommandReceiver();
		}
		cometReceiver.receivers.add(receiver);
	}

	@Override
	public void receiveResponse(CometMessage message) {
		if (message instanceof CometCommandResponse) {
			CometCommandResponse response = (CometCommandResponse) message;
			for (ReceiveMapper receiver : receivers) {
				receiver.receiveValues(response);
			}
		}
	}

}
