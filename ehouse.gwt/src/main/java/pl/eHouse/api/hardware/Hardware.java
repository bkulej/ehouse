/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.hardware;

import java.util.Collection;
import java.util.HashMap;

import pl.eHouse.api.message.Address;

/**
 * 
 * @author Bartek
 */
public class Hardware {

	private String serial;
	private String softwareType;
	private String hardwareType;
	private Address address;
	private String description;
	private HashMap<Integer, Device> devices;

	public Hardware(String serial, String softwareType, String hardwareType)
			throws HardwareException {
		if (serial.length() != 8) {
			throw new HardwareException("Bad serial number");
		}
		if (softwareType.length() != 4) {
			throw new HardwareException("Bad software type");
		}
		if (hardwareType.length() != 4) {
			throw new HardwareException("Bad hardware type");
		}
		this.serial = serial;
		this.softwareType = softwareType;
		this.hardwareType = hardwareType;
		this.description = "";
	}

	public String getId() {
		return hardwareType + softwareType + serial;
	}

	public String getSerial() {
		return serial;
	}

	public String getHardwareType() {
		return hardwareType;
	}

	public String getSoftwareType() {
		return softwareType;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public synchronized void addDevice(Device device) {
		if (devices == null) {
			devices = new HashMap<Integer, Device>();
		}
		devices.put(device.getNumber(), device);
	}

	public synchronized Device getDevice(int number) {
		return (devices == null) ? null : devices.get(number);
	}

	public synchronized Collection<Device> getDevices() {
		return devices == null ? null : devices.values();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Hardware other = (Hardware) obj;
		if ((this.serial == null) ? (other.serial != null) : !this.serial
				.equals(other.serial)) {
			return false;
		}
		if ((this.softwareType == null) ? (other.softwareType != null)
				: !this.softwareType.equals(other.softwareType)) {
			return false;
		}
		if ((this.hardwareType == null) ? (other.hardwareType != null)
				: !this.hardwareType.equals(other.hardwareType)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + (this.serial != null ? this.serial.hashCode() : 0);
		hash = 83
				* hash
				+ (this.softwareType != null ? this.softwareType.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return hardwareType + softwareType + serial;
	}

}
