package pl.np.ehouse.core.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.np.ehouse.core.message.Commands;

public class CommandsTest {

	@Test
	void testIsRequest() {
		try {
			Assertions.assertTrue(Commands.isRequest(Commands.REQUEST_BOOT_MODE));
			Assertions.assertTrue(Commands.isRequest(Commands.REQUEST_COMMAND_10));
			Assertions.assertTrue(Commands.isRequest(Commands.REQUEST_SYSTEM_ALARM));
			Assertions.assertTrue(!Commands.isRequest(Commands.RESPONSE_OK));
			Assertions.assertTrue(!Commands.isRequest(Commands.RESPONSE_ERROR));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testIsBootRequest() {
		try {
			Assertions.assertTrue(Commands.isBootRequest(Commands.BOOT_REQUEST_BEGIN));
			Assertions.assertTrue(Commands.isBootRequest(Commands.BOOT_REQUEST_END));
			Assertions.assertTrue(Commands.isBootRequest(Commands.BOOT_REQUEST_FIND));
			Assertions.assertTrue(!Commands.isBootRequest(Commands.BOOT_RESPONSE_OK));
			Assertions.assertTrue(!Commands.isBootRequest(Commands.BOOT_RESPONSE_ERROR));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
