package pl.eHouse.web.common.client.command;

import java.util.ArrayList;

import pl.eHouse.web.common.client.comet.CometSender;
import pl.eHouse.web.common.client.comet.messages.CometCommandMessage;

import com.google.gwt.user.client.Timer;

public class CommandSender extends Timer {

	private ArrayList<CometCommandMessage> messages;
	private int index;

	public CommandSender() {
		super();
		this.messages = new ArrayList<CometCommandMessage>();
		this.index = 0;
	}

	public void add(String name, String values) {
		messages.add(new CometCommandMessage(name, values));
	}

	public void start() {
		for(CometCommandMessage message: messages) {
			CometSender.send(message);
		}
		scheduleRepeating(1000);
	}

	public void stop() {
		cancel();
	}

	@Override
	public void run() {
		if (index < messages.size()) {
			CometSender.send(messages.get(index));
			++index;
		} else if (index > messages.size()) {
			index = 0;
		} else {
			++index;
		}
	}

}
