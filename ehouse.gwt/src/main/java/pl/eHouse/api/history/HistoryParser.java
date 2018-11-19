package pl.eHouse.api.history;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import pl.eHouse.api.config.SettingsException;

public class HistoryParser {

	private FileReader file;
	private BufferedReader reader;
	private HashSet<String> lookers;
	private DateFormat format;
	private String fileName;
	ArrayList<HistoryValue> history;

	public HistoryParser(String fileName) throws FileNotFoundException {
		super();
		this.fileName = fileName;
		this.lookers = new HashSet<String>();
		this.history = new ArrayList<HistoryValue>();
		this.format = new SimpleDateFormat(HistoryListener.DATE_APPENDER);
	}

	public List<HistoryValue> find(Date start, Date stop, String addresses,
			String commands) throws SettingsException {
		lookers.clear();
		history.clear();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();
		calendar.setTime(start);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		while (!calendar.getTime().after(stop)) {
			find(calendar.getTime().equals(today) ? fileName : fileName + "."
					+ format.format(calendar.getTime()), addresses, commands);
			calendar.add(Calendar.DATE, 1);
		}

		return history;
	}

	private void find(String fileName, String addresses, String commands) {
		try {
			openFile(fileName);
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				try {
					HistoryValue value = new HistoryValue(line);
					if (isAddressMessage(value) && !isLookRequest(value)
							&& !isLookResponse(value)
							&& checkAddress(value, addresses)
							&& checkCommand(value, commands)) {
						history.add(0, value);
					}
				} catch (HistoryException e) {
				}
			}
		} catch (IOException e) {
		} finally {
			closeFile();
		}
	}

	private boolean isAddressMessage(HistoryValue value) {
		return value.getAdd() != null;
	}

	private boolean isLookRequest(HistoryValue value) {
		if (value.getAsd().equals("0001") && value.getCommand().equals("10")) {
			lookers.add(value.getAdd() + value.getId());
			return true;
		}
		return false;
	}

	private boolean isLookResponse(HistoryValue value) {
		if (value.getAdd().equals("0001") && value.getCommand().equals("00")) {
			return lookers.remove(value.getAsd() + value.getId());
		}
		return false;
	}

	private boolean checkAddress(HistoryValue value, String addresses) {
		if (addresses == null || addresses.length() == 0) {
			return true;
		}
		if (addresses.contains(value.getAdd())) {
			return true;
		}
		if (addresses.contains(value.getAsd())) {
			return true;
		}
		return false;
	}

	private boolean checkCommand(HistoryValue value, String commands) {
		if (commands == null || commands.length() == 0) {
			return true;
		}
		if (commands.contains(value.getCommand())) {
			return true;
		}
		return false;
	}

	private void openFile(String fileName) throws FileNotFoundException {
		file = new FileReader(fileName);
		reader = new BufferedReader(file);
	}

	private void closeFile() {
		try {
			if (reader != null) {
				reader.close();
			}
			if (file != null) {
				file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
