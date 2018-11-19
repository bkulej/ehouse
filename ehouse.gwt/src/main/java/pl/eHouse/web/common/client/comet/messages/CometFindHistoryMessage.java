package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometFindHistoryMessage extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String start;
	private String stop;
	private String addresses;
	private String commands;

	public CometFindHistoryMessage() {
		super();
	}

	public CometFindHistoryMessage(String start, String stop, String addresses,
			String commands) {
		super();
		this.start = start;
		this.stop = stop;
		this.addresses = addresses;
		this.commands = commands;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		return "CometFindHistoryMessage [start=" + start + ", stop=" + stop
				+ ", addresses=" + addresses + ", commands=" + commands + "]";
	}

}
