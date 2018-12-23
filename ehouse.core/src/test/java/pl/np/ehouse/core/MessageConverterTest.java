package pl.np.ehouse.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.MessageException;
import pl.np.ehouse.core.message.MessageCoverter;
import pl.np.ehouse.core.utils.DataConvertException;

/**
 * 
 * @author Bartek
 *
 */
class MessageConverterTest {

	@Test
	void testConvertSerialMessageFromListWithoutData() throws MessageException, DataConvertException {
		List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
		data.add(0, 0x01);
		Message message = MessageCoverter.listToMessage(data);
		assertEquals(0x01234567L, message.getSerial(), "Bad serial");
		assertEquals(0x89, message.getId(), "Bad id");
		assertEquals(0x12, message.getCommand(), "Bad command");
		assertNull(message.getData());
	}

	@Test
	void testConvertSerialMessageFromListWithData() throws MessageException, DataConvertException {
		List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
				.collect(Collectors.toList());
		data.add(0, 0x01);
		Message message = MessageCoverter.listToMessage(data);
		assertEquals(0x01234567L, message.getSerial(), "Bad serial");
		assertEquals(0x89, message.getId(), "Bad id");
		assertEquals(0x12, message.getCommand(), "Bad command");
		assertArrayEquals(new Integer[] { 0x11, 0x22, 0x33 }, message.getData().toArray(new Integer[0]));
	}

	@Test
	void testConvertAddressMessageFromListWithoutData() throws MessageException, DataConvertException {
		List<Integer> data = "012345678912".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
		data.add(0, 0x02);
		Message message = MessageCoverter.listToMessage(data);
		assertEquals(0x0123, message.getAdd(), "Bad add");
		assertEquals(0x4567, message.getAsd(), "Bad asd");
		assertEquals(0x89, message.getId(), "Bad id");
		assertEquals(0x12, message.getCommand(), "Bad command");
		assertNull(message.getData());
	}

	@Test
	void testConvertAddressMessageFromListWithData() throws MessageException, DataConvertException {
		List<Integer> data = "012345678912112233".chars().mapToObj(value -> (Integer) value)
				.collect(Collectors.toList());
		data.add(0, 0x02);
		Message message = MessageCoverter.listToMessage(data);
		assertEquals(0x0123, message.getAdd(), "Bad add");
		assertEquals(0x4567, message.getAsd(), "Bad asd");
		assertEquals(0x89, message.getId(), "Bad id");
		assertEquals(0x12, message.getCommand(), "Bad command");
		assertArrayEquals(new Integer[] { 0x11, 0x22, 0x33 }, message.getData().toArray(new Integer[0]));
	}

}
