package pl.np.ehouse.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.MessageConverter;
import pl.np.ehouse.core.message.MessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bartek
 */
class MessageConverterTest {

    @Test
    void testGetByteFromHexAsciiList() {
        try {
            List<Integer> data = "0ACD120B".chars().boxed().collect(Collectors.toList());
            Assertions.assertEquals(0x0A, MessageConverter.getByteFromHexAsciiList(data, 0));
            Assertions.assertEquals(0xCD, MessageConverter.getByteFromHexAsciiList(data, 2));
            Assertions.assertEquals(0x12, MessageConverter.getByteFromHexAsciiList(data, 4));
            Assertions.assertEquals(0x0B, MessageConverter.getByteFromHexAsciiList(data, 6));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getByteFromHexAsciiList(data, 7));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getByteFromHexAsciiList(data, 8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetWordFromHexAsciiList() {
        try {
            List<Integer> data = "0ACD120B".chars().boxed().collect(Collectors.toList());
            Assertions.assertEquals(0x0ACD, MessageConverter.getWordFromHexAsciiList(data, 0));
            Assertions.assertEquals(0x120B, MessageConverter.getWordFromHexAsciiList(data, 4));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getWordFromHexAsciiList(data, 5));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getWordFromHexAsciiList(data, 8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetDoubleFromHexAsciiList() {
        try {
            List<Integer> data = "0ACD120B10B3113F".chars().boxed()
                    .collect(Collectors.toList());
            Assertions.assertEquals(0x0ACD120BL, MessageConverter.getDoubleFromHexAsciiList(data, 0));
            Assertions.assertEquals(0x10B3113FL, MessageConverter.getDoubleFromHexAsciiList(data, 8));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getDoubleFromHexAsciiList(data, 9));
            Assertions.assertThrows(MessageException.class, () -> MessageConverter.getDoubleFromHexAsciiList(data, 16));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testListToSerialMessageWithoutData() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageConverter.listToMessage(data);
            Assertions.assertEquals(0x01234567L, message.getSerial(), "Bad serial");
            Assertions.assertEquals(0x89, message.getId(), "Bad id");
            Assertions.assertEquals(0x12, message.getCommand(), "Bad command");
            Assertions.assertNull(message.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testListToSerialMessageWithData() {
        try {
            List<Integer> data = "012345678912112233".chars().boxed()
                    .collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageConverter.listToMessage(data);
            Assertions.assertEquals(0x01234567L, message.getSerial(), "Bad serial");
            Assertions.assertEquals(0x89, message.getId(), "Bad id");
            Assertions.assertEquals(0x12, message.getCommand(), "Bad command");
            Assertions.assertArrayEquals(new Integer[]{0x11, 0x22, 0x33}, message.getData().toArray(new Integer[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testListToAddressMessageWithoutData() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x02);
            Message message = MessageConverter.listToMessage(data);
            Assertions.assertEquals(0x0123, message.getAdd(), "Bad add");
            Assertions.assertEquals(0x4567, message.getAsd(), "Bad asd");
            Assertions.assertEquals(0x89, message.getId(), "Bad id");
            Assertions.assertEquals(0x12, message.getCommand(), "Bad command");
            Assertions.assertNull(message.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testListToAddressMessageWithData() {
        try {
            List<Integer> data = "012345678912112233".chars().boxed()
                    .collect(Collectors.toList());
            data.add(0, 0x02);
            Message message = MessageConverter.listToMessage(data);
            Assertions.assertEquals(0x0123, message.getAdd(), "Bad add");
            Assertions.assertEquals(0x4567, message.getAsd(), "Bad asd");
            Assertions.assertEquals(0x89, message.getId(), "Bad id");
            Assertions.assertEquals(0x12, message.getCommand(), "Bad command");
            Assertions.assertArrayEquals(new Integer[]{0x11, 0x22, 0x33}, message.getData().toArray(new Integer[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddDataToHexAsciiList() {
        try {
            List<Integer> data = new ArrayList<>();
            MessageConverter.addByteToHexAsciiList(data, 0x01);
            MessageConverter.addByteToHexAsciiList(data, 0x23);
            MessageConverter.addWordToHexAsciiList(data, 0x4567);
            MessageConverter.addDoubleToHexAsciiList(data, 0x89ABCDEF);
            List<Integer> result = "0123456789ABCDEF".chars().boxed()
                    .collect(Collectors.toList());
            Assertions.assertIterableEquals(result, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSerialMessageToListWithoutData() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageConverter.listToMessage(data);
            List<Integer> result = MessageConverter.messageToList(message);
            Assertions.assertIterableEquals(data, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSerialMessageWithDataToList() {
        try {
            List<Integer> data = "012345678912112233".chars().boxed()
                    .collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageConverter.listToMessage(data);
            List<Integer> result = MessageConverter.messageToList(message);
            Assertions.assertIterableEquals(data, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddressMessageWithoutDataToList() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x02);
            Message message = MessageConverter.listToMessage(data);
            List<Integer> result = MessageConverter.messageToList(message);
            Assertions.assertIterableEquals(data, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddressMessageWithDataToList() {
        try {
            List<Integer> data = "012345678912112233".chars().boxed()
                    .collect(Collectors.toList());
            data.add(0, 0x02);
            Message message = MessageConverter.listToMessage(data);
            List<Integer> result = MessageConverter.messageToList(message);
            Assertions.assertIterableEquals(data, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
