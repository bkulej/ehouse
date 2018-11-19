package pl.eHouse.web.common.client.comet.messages;

import pl.eHouse.web.common.client.comet.CometMessage;

public class CometFindHistoryResponse extends CometMessage {

	private static final long serialVersionUID = -3264740979351883877L;

	private String date;
	private String type;
	private String add;
	private String asd;
	private String id;
	private String command;
	private String data;

	public CometFindHistoryResponse() {
		super();
	}

	public CometFindHistoryResponse(String date, String type, String add,
			String asd, String id, String command, String data) {
		super();
		this.date = date;
		this.type = type;
		this.add = add;
		this.asd = asd;
		this.id = id;
		this.command = command;
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getAsd() {
		return asd;
	}

	public void setAsd(String asd) {
		this.asd = asd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CometFindHistoryResponse [date=" + date + ", type=" + type
				+ ", add=" + add + ", asd=" + asd + ", id=" + id + ", command="
				+ command + ", data=" + data + "]";
	}

}
