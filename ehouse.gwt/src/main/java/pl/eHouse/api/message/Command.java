/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.message;

import pl.eHouse.api.utils.ConvertException;
import pl.eHouse.api.utils.ConvertUtil;

/**
 * 
 * @author Bartek
 */
public class Command {

	public final static Command RESPONSE_OK = new Command(0x00);
	public final static Command REQUEST_SYSTEM_RESTART = new Command(0x01);
	public final static Command REQUEST_SYSTEM_INFO = new Command(0x02);
	public final static Command REQUEST_NETWORK_FIND = new Command(0x03);
	public final static Command REQUEST_NETWORK_ALLOCATE = new Command(0x04);
	public final static Command REQUEST_EEPROM_GET = new Command(0x05);
	public final static Command REQUEST_EEPROM_SET = new Command(0x06);
	public final static Command REQUEST_EEPROM_STATUS = new Command(0x07);
	public final static Command REQUEST_CONFIG_SET = new Command(0x08);
	public final static Command REQUEST_BOOT_MODE = new Command(0x09);
	public final static Command REQUEST_SYSTEM_ALARM = new Command(0x0A);
	public final static Command REQUEST_WATCHDOG_RESET = new Command(0x0B);
	public final static Command RESPONSE_ERROR = new Command(0x0F);

	private int command;

	public Command() {
		this.command = 0;
	}

	public Command(int command) {
		this.command = command;
	}

	public Command(String command) throws ConvertException {
		this.command = ConvertUtil.hexToInt(command, "Bad command");
	}

	public int getInt() {
		return command;
	}

	public boolean equals(Command command) {
		return this.command == command.getInt();
	}

	public void setDigit(char value, int number) {
		this.command |= ConvertUtil.hexToInt(value, (number * 4));
	}

	public char getDigit(int number) {
		return ConvertUtil.intToDigit(this.command, number);
	}

	public boolean isRequest() {
		return (command != 0x00) && (command != 0x0F);
	}


	@Override
	public String toString() {
		return ConvertUtil.byteToHex(command);
	}

}
