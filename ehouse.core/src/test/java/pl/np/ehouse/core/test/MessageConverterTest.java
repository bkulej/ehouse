package pl.np.ehouse.core.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.MessageConverter;
import pl.np.ehouse.core.message.MessageException;
import pl.np.ehouse.core.utils.DataConvertException;

/**
 * 
 * @author Bartek
 *
 */
class MessageConverterTest {
	
	@Test
	void testGetByteFromHexAsciiList() {
		try {
			List<Integer> data = "0ACD120B".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			assertEquals(0x0A, MessageConverter.getByteFromHexAsciiList(data, 0));
			assertEquals(0xCD, MessageConverter.getByteFromHexAsciiList(data, 2));
			assertEquals(0x12, MessageConverter.getByteFromHexAsciiList(data, 4));
			assertEquals(0x0B, MessageConverter.getByteFromHexAsciiList(data, 6));
			assertThrows(MessageException.class, () -> MessageConverter.getByteFromHexAsciiList(data, 7));
			assertThrows(MessageException.class, () -> MessageConverter.getByteFromHexAsciiList(data, 8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetWordFromHexAsciiList() {
		try {
			List<Integer> data = "0ACD120B".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			assertEquals(0x0ACD, MessageConverter.getWordFromHexAsciiList(data, 0));
			assertEquals(0x120B, MessageConverter.getWordFromHexAsciiList(data, 4));
			assertThrows(MessageException.class, () -> MessageConverter.getWordFromHexAsciiList(data, 5));
			assertThrows(MessageException.class, () -> MessageConverter.getWordFromHexAsciiList(data, 8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetDoubleFromHexAsciiList() {
		try {
			List<Integer> data = "0ACD120B10B3113F".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			assertEquals(0x0ACD120BL, MessageConverter.getDoubleFromHexAsciiList(data, 0));
			assertEquals(0x10B3113FL, MessageConverter.getDoubleFromHexAsciiList(data, 8));
			assertThrows(MessageException.class, () -> MessageConverter.getDoubleFromHexAsciiList(data, 9));
			assertThrows(MessageException.class, () -> MessageConverter.getDoubleFromHexAsciiList(data, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testListToSerialMessageWithoutData() {
		try {
			List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			data.add(0, 0x01);
			Message message = MessageConverter.listToMessage(data);
			assertEquals(0x01234567L, message.getSerial(), "Bad serial");
			assertEquals(0x89, message.getId(), "Bad id");
			assertEquals(0x12, message.getCommand(), "Bad command");
			assertNull(message.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testListToSerialMessageWithData() {
		try {
			List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			data.add(0, 0x01);
			Message message = MessageConverter.listToMessage(data);
			assertEquals(0x01234567L, message.getSerial(), "Bad serial");
			assertEquals(0x89, message.getId(), "Bad id");
			assertEquals(0x12, message.getCommand(), "Bad command");
			assertArrayEquals(new Integer[] { 0x11, 0x22, 0x33 }, message.getData().toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testListToAddressMessageWithoutData() {
		try {
			List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			data.add(0, 0x02);
			Message message = MessageConverter.listToMessage(data);
			assertEquals(0x0123, message.getAdd(), "Bad add");
			assertEquals(0x4567, message.getAsd(), "Bad asd");
			assertEquals(0x89, message.getId(), "Bad id");
			assertEquals(0x12, message.getCommand(), "Bad command");
			assertNull(message.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testListToAddressMessageWithData() {
		try {
			List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			data.add(0, 0x02);
			Message message = MessageConverter.listToMessage(data);
			assertEquals(0x0123, message.getAdd(), "Bad add");
			assertEquals(0x4567, message.getAsd(), "Bad asd");
			assertEquals(0x89, message.getId(), "Bad id");
			assertEquals(0x12, message.getCommand(), "Bad command");
			assertArrayEquals(new Integer[] { 0x11, 0x22, 0x33 }, message.getData().toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	void testAddDataToHexAsciiList() throws DataConvertException {
		try {
			List<Integer> data = new ArrayList<Integer>();
			MessageConverter.addByteToHexAsciiList(data, 0x01);
			MessageConverter.addByteToHexAsciiList(data, 0x23);
			MessageConverter.addWordToHexAsciiList(data, 0x4567);
			MessageConverter.addDoubleToHexAsciiList(data, 0x89ABCDEF);
			List<Integer> result = "0123456789ABCDEF".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			assertArrayEquals(result.toArray(new Integer[0]), data.toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testSerialMessageToListWithoutData() {
		try {
			List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			data.add(0, 0x01);
			Message message = MessageConverter.listToMessage(data);
			List<Integer> result = MessageConverter.messageToList(message);
			assertArrayEquals(data.toArray(new Integer[0]), result.toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testSerialMessageWithDataToList() {
		try {
			List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			data.add(0, 0x01);
			Message message = MessageConverter.listToMessage(data);
			List<Integer> result = MessageConverter.messageToList(message);
			assertArrayEquals(data.toArray(new Integer[0]), result.toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddressMessageWithoutDataToList() {
		try {
			List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
			data.add(0, 0x02);
			Message message = MessageConverter.listToMessage(data);
			List<Integer> result = MessageConverter.messageToList(message);
			assertArrayEquals(data.toArray(new Integer[0]), result.toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddressMessageWithDataToList() {
		try {
			List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
					.collect(Collectors.toList());
			data.add(0, 0x02);
			Message message = MessageConverter.listToMessage(data);
			List<Integer> result = MessageConverter.messageToList(message);
			assertArrayEquals(data.toArray(new Integer[0]), result.toArray(new Integer[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
