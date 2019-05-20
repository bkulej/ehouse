package pl.np.ehouse.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.MessageFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bartek
 */
class MessageFactoryTest {

    @Test
    void testListToSerialMessageWithoutData() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageFactory.toMessage(data);
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
            Message message = MessageFactory.toMessage(data);
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
            Message message = MessageFactory.toMessage(data);
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
            Message message = MessageFactory.toMessage(data);
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
    void testSerialMessageToListWithoutData() {
        try {
            List<Integer> data = "012345678912".chars().boxed().collect(Collectors.toList());
            data.add(0, 0x01);
            Message message = MessageFactory.toMessage(data);
            List<Integer> result = MessageFactory.toList(message);
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
            Message message = MessageFactory.toMessage(data);
            List<Integer> result = MessageFactory.toList(message);
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
            Message message = MessageFactory.toMessage(data);
            List<Integer> result = MessageFactory.toList(message);
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
            Message message = MessageFactory.toMessage(data);
            List<Integer> result = MessageFactory.toList(message);
            Assertions.assertIterableEquals(data, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
