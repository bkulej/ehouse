package pl.eHouse.api.cron;

import pl.eHouse.api.message.Address;
import pl.eHouse.api.message.Command;
import pl.eHouse.api.utils.ConvertException;

public class CronTask {
	
	private String name;
	private String patern;
	private Address address;
	private Command command;
	private String data;
	
	public CronTask(String name, String patern, String address,
			String command, String data) throws ConvertException {
		super();
		this.name = name;
		this.patern = patern;
		this.address = new Address(address);
		this.command = new Command(command);
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatern() {
		return patern;
	}

	public void setPatern(String patern) {
		this.patern = patern;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
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
		return "CronTask [name=" + name + ", patern=" + patern + ", address="
				+ address + ", command=" + command + ", data=" + data + "]";
	}

}
