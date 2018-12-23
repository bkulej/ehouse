package pl.np.ehouse.core.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bartek
 *
 */
public class MessageImpl implements Message {

	public final static int TYPE_SERIAL = 0x01;
	public final static int TYPE_ADDRESS = 0x02;
	public final static int TYPE_BOOT = 0x03;

	private int type;
	private long serial;
	private int asd;
	private int add;
	private int id;
	private int command;
	private List<Integer> data;

	/**
	 * 
	 * @param type
	 * @throws MessageException
	 */
	public MessageImpl(int type) throws MessageException {
		super();
		setType(type);
	}

	/*
	 * 
	 */
	private void setType(int type) throws MessageException {
		if (type == TYPE_SERIAL || type == TYPE_ADDRESS || type == TYPE_BOOT) {
			this.type = type;
		} else {
			throw new MessageException("Bad message type");
		}
	}

	/**
	 * 
	 */
	public int getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public long getSerial() throws MessageException {
		if (type != TYPE_SERIAL && type != TYPE_BOOT) {
			throw new MessageException("Bad message type");
		}
		return serial;
	}

	/**
	 * 
	 * @param serial
	 * @throws MessageException
	 */
	public void setSerial(long serial) throws MessageException {
		if (type != TYPE_SERIAL && type != TYPE_BOOT) {
			throw new MessageException("Bad message type");
		}
		this.serial = serial;
	}

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public int getAsd() throws MessageException {
		if (type != TYPE_ADDRESS) {
			throw new MessageException("Bad message type");
		}
		return asd;
	}

	/**
	 * 
	 * @param asd
	 * @throws MessageException
	 */
	public void setAsd(int asd) throws MessageException {
		if (type != TYPE_ADDRESS) {
			throw new MessageException("Bad message type");
		}
		this.asd = asd;
	}

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public int getAdd() throws MessageException {
		if (type != TYPE_ADDRESS) {
			throw new MessageException("Bad message type");
		}
		return add;
	}

	/**
	 * 
	 * @param add
	 * @throws MessageException
	 */
	public void setAdd(int add) throws MessageException {
		if (type != TYPE_ADDRESS) {
			throw new MessageException("Bad message type");
		}
		this.add = add;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * 
	 * @param command
	 */
	public void setCommand(int command) {
		this.command = command;
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void addData(int value) {
		if (data == null) {
			data = new ArrayList<>();
		}
		data.add(value);
	}

	/**
	 * 
	 */
	public boolean isSerial() {
		return type == TYPE_SERIAL;
	}

	/**
	 * 
	 */
	public boolean isAddress() {
		return type == TYPE_ADDRESS;
	}

	/**
	 * 
	 */
	public boolean isBoot() {
		return type == TYPE_BOOT;
	}

}
