/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.hardware;

import pl.eHouse.api.message.Address;
import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 * 
 * @author Bartek
 */
public class Device {

	private Hardware hardware;
	private int number;
	private int type;
	private String name;
	private String description;

	/**
	 * Konstruktor
	 * 
	 * @param hardware
	 * @param number
	 * @param type
	 */
	public Device(Hardware hardware, String number, String type, String name,
			String description) throws ConvertException {
		this.hardware = hardware;
		this.number = ConvertUtil.hexToInt(number, "Bad device address");
		this.type = ConvertUtil.hexToInt(type, "Bad device type");
		this.name = name;
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public int getType() {
		return type;
	}

	public Hardware getHardware() {
		return hardware;
	}

	public Address getAddress() {
		return hardware.getAddress() != null ? new Address(hardware
				.getAddress().getInt() + this.number) : null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return hardware + ConvertUtil.byteToHex(number);
	}

}
