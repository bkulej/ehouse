package pl.np.ehouse.core.message;

import java.util.ArrayList;
import java.util.List;

import pl.np.ehouse.core.util.PrintFormater;

/**
 * @author Bartek
 */
public class Message {

	private int type;
	private int serial;
	private int asd;
	private int add;
	private int id;
	private int command;
	private List<Integer> data;

	/**
	 * @param type -
	 * @throws MessageException -
	 */
	public Message(int type) {
		super();
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the serial
	 */
	public int getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(int serial) {
		this.serial = serial;
	}

	/**
	 * @return the asd
	 */
	public int getAsd() {
		return asd;
	}

	/**
	 * @param asd the asd to set
	 */
	public void setAsd(int asd) {
		this.asd = asd;
	}

	/**
	 * @return the add
	 */
	public int getAdd() {
		return add;
	}

	/**
	 * @param add the add to set
	 */
	public void setAdd(int add) {
		this.add = add;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the command
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(int command) {
		this.command = command;
	}

	/**
	 * @return -
	 */
	public List<Integer> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	/**
	 * @param value -
	 */
	public void addData(int value) {
		if (data == null) {
			data = new ArrayList<>();
		}
		data.add(value);
	}

	@Override
	public String toString() {
		return PrintFormater.formatMessage(this);

	}

}
