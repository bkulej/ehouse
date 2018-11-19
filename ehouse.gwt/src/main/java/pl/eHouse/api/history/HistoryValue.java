package pl.eHouse.api.history;

public class HistoryValue {

	private String date;
	private String type;
	private String add;
	private String asd;
	private String id;
	private String command;
	private String data;

	public HistoryValue(String line) throws HistoryException {
		if (line.length() < 70) {
			throw new HistoryException("Line to short");
		}
		int dateLength = HistoryListener.DATE_FORMAT.length();
		date = line.substring(0, dateLength);
		type = line.substring(dateLength + 2, dateLength + 4);
		if (type.equals("IA") || type.equals("OA") ) {
			add = line.substring(dateLength + 10, dateLength + 14);
			asd = line.substring(dateLength + 19, dateLength + 23);
			id = line.substring(dateLength + 27, dateLength + 29);
			command = line.substring(dateLength + 38, dateLength + 40);
			data = line.substring(dateLength + 46, line.length() - 1);
		}
	}

	public String getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public String getAdd() {
		return add;
	}

	public String getAsd() {
		return asd;
	}

	public String getId() {
		return id;
	}

	public String getCommand() {
		return command;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "HistoryValue [date=" + date + ", type=" + type + ", add=" + add
				+ ", asd=" + asd + ", id=" + id + ", command=" + command
				+ ", data=" + data + "]";
	}

}
