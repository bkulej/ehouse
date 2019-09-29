package pl.np.ehouse.core.message;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Bartek
 */
class MessagesTest {

	@Test
	void testIsMessageToServerCorrectSerial() {
		try {
			List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.SERIAL);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isMessageToServer(message), "Message should be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsMessageToServerCorrectBoot() {
		try {
			List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.BOOT);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isMessageToServer(message), "Message should be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsMessageToServerCorrectAddress01() {
		try {
			List<Integer> data = "000011112212".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isMessageToServer(message), "Message should be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsMessageToServerCorrectAddress02() {
		try {
			List<Integer> data = "000111112212".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isMessageToServer(message), "Message should be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsMessageToServerBadAddress01() {
		try {
			List<Integer> data = "000211112212".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isMessageToServer(message), "Message shoduld not be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsMessageToServerBadAddress02() {
		try {
			List<Integer> data = "FFFF11112212".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isMessageToServer(message), "Message shoduld not be to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectSerial01() {
		try {
			List<Integer> data = "111111112201".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.SERIAL);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectSerial02() {
		try {
			List<Integer> data = "111111112207".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.SERIAL);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadSerial03() {
		try {
			List<Integer> data = "111111112200".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.SERIAL);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadSerial04() {
		try {
			List<Integer> data = "11111111220F".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.SERIAL);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void testIsRequestToServerCorrectBoot01() {
		try {
			List<Integer> data = "111111112201".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.BOOT);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectBoot02() {
		try {
			List<Integer> data = "111111112207".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.BOOT);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadBoot03() {
		try {
			List<Integer> data = "111111112210".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.BOOT);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadBoot04() {
		try {
			List<Integer> data = "11111111221F".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.BOOT);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectAddress01() {
		try {
			List<Integer> data = "0000CCCC2201".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectAddress02() {
		try {
			List<Integer> data = "0001CCCC2207".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectAddress03() {
		try {
			List<Integer> data = "0000CCCC2201".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerCorrectAddress04() {
		try {
			List<Integer> data = "0001CCCC2207".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadAddress01() {
		try {
			List<Integer> data = "0002CCCC2201".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadAddress02() {
		try {
			List<Integer> data = "0002CCCC2207".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadAddress03() {
		try {
			List<Integer> data = "0000CCCC2200".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsRequestToServerBadAddress04() {
		try {
			List<Integer> data = "0001CCCC220F".chars().boxed().collect(Collectors.toList());
			data.add(0, Types.ADDRESS);
			Message message = MessageFactory.fromList(data);
			Assertions.assertTrue(!Messages.isRequestToServer(message), "Message should be request to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
