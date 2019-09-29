package pl.np.ehouse.core.message;

/**
 * 
 * @author bkulejewski
 *
 */
public class Commands {

	public final static int RESPONSE_OK = 0x00;
	public final static int REQUEST_SYSTEM_RESTART = 0x01;
	public final static int REQUEST_SYSTEM_INFO = 0x02;
	public final static int REQUEST_NETWORK_FIND = 0x03;
	public final static int REQUEST_NETWORK_ALLOCATE = 0x04;
	public final static int REQUEST_EEPROM_GET = 0x05;
	public final static int REQUEST_EEPROM_SET = 0x06;
	public final static int REQUEST_EEPROM_STATUS = 0x07;
	public final static int REQUEST_CONFIG_SET = 0x08;
	public final static int REQUEST_BOOT_MODE = 0x09;
	public final static int REQUEST_SYSTEM_ALARM = 0x0A;
	public final static int REQUEST_WATCHDOG_RESET = 0x0B;
	public final static int REQUEST_COMMUNICATION_TEST = 0x0C;
	public final static int RESPONSE_ERROR = 0x0F;
	
	public final static int REQUEST_COMMAND_10 = 0x10;
	public final static int REQUEST_COMMAND_11 = 0x11;
	public final static int REQUEST_COMMAND_12 = 0x12;
	public final static int REQUEST_COMMAND_13 = 0x13;
	public final static int REQUEST_COMMAND_14 = 0x14;
	public final static int REQUEST_COMMAND_15 = 0x15;
	public final static int REQUEST_COMMAND_16 = 0x16;
	public final static int REQUEST_COMMAND_17 = 0x17;
	public final static int REQUEST_COMMAND_18 = 0x18;
	public final static int REQUEST_COMMAND_19 = 0x19;
	public final static int REQUEST_COMMAND_1A = 0x1A;
	public final static int REQUEST_COMMAND_1B = 0x1B;
	public final static int REQUEST_COMMAND_1C = 0x1C;
	public final static int REQUEST_COMMAND_1D = 0x1D;
	public final static int REQUEST_COMMAND_1E = 0x1E;
	public final static int REQUEST_COMMAND_1F = 0x1F;

	public final static int BOOT_RESPONSE_OK = 0x10;
	public final static int BOOT_REQUEST_FIND = 0x11;
	public final static int BOOT_REQUEST_BEGIN = 0x12;
	public final static int BOOT_REQUEST_SAVE = 0x13;
	public final static int BOOT_REQUEST_END = 0x14;
	public final static int BOOT_REQUEST_START = 0x15;
	public final static int BOOT_RESPONSE_ERROR = 0x1F;

	/**
	 * 
	 * @param command
	 * @return
	 */
	public static boolean isRequest(int command) {
		return (command != RESPONSE_OK && command != RESPONSE_ERROR);
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	public static boolean isBootRequest(int command) {
		return (command != BOOT_RESPONSE_OK && command != BOOT_RESPONSE_ERROR);
	}

}
