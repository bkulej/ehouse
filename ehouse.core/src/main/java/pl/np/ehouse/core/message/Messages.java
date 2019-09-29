package pl.np.ehouse.core.message;

/**
 * 
 * @author bkulejewski
 *
 */
public class Messages {

	/**
	 * 
	 * @param messageIn
	 * @return
	 */
	public static boolean isMessageToServer(Message messageIn) {
		if (messageIn.getType() == Types.ADDRESS) {
			return (messageIn.getAdd() == Addresses.ADDRESS_COMPUTER
					|| messageIn.getAdd() == Addresses.ADDRESS_BROADCAST);
		}
		return true;
	}

	/**
	 * 
	 * @param messageIn
	 * @return
	 */
	public static boolean isRequestToServer(Message messageIn) {
		if (messageIn.getType() == Types.BOOT) {
			return (isMessageToServer(messageIn) && Commands.isBootRequest(messageIn.getCommand()));
		} else {
			return (isMessageToServer(messageIn) && Commands.isRequest(messageIn.getCommand()));
		}
	}

	/**
	 * 
	 * @param messageIn
	 * @return
	 */
	public static boolean isResponseToMessage(Message messageOut, Message messageIn) {
		return (messageOut.getType() == messageIn.getType() && messageOut.getId() == messageIn.getId()
				&& messageOut.getAdd() == messageIn.getAsd());
	}

}
