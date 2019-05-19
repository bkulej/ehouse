package pl.np.ehouse.core.message;

import pl.np.ehouse.core.utils.DataConvertException;
import pl.np.ehouse.core.utils.DataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartek
 */
public class MessageConverter {

    /**
     * @param data -
     * @return -
     * @throws MessageException -
     * @throws DataConvertException -
     */
    public static Message listToMessage(List<Integer> data) throws MessageException, DataConvertException {
        MessageImpl message = new MessageImpl(data.get(0));
        if (message.isAddress()) {
            message.setAdd(MessageConverter.getWordFromHexAsciiList(data, 1));
            message.setAsd(MessageConverter.getWordFromHexAsciiList(data, 5));
        } else {
            message.setSerial(MessageConverter.getDoubleFromHexAsciiList(data, 1));
        }
        message.setId(MessageConverter.getByteFromHexAsciiList(data, 9));
        message.setCommand(MessageConverter.getByteFromHexAsciiList(data, 11));
        for (int i = 13; i < data.size(); i += 2) {
            message.addData(MessageConverter.getByteFromHexAsciiList(data, i));
        }
        return message;
    }

    /**
     * @param message -
     * @return -
     * @throws MessageException -
     * @throws DataConvertException -
     */
    public static List<Integer> messageToList(Message message) throws DataConvertException, MessageException {
        List<Integer> data = new ArrayList<>();
        data.add(message.getType());
        if (message.isAddress()) {
            addWordToHexAsciiList(data, message.getAdd());
            addWordToHexAsciiList(data, message.getAsd());
        } else {
            addDoubleToHexAsciiList(data, message.getSerial());
        }
        addByteToHexAsciiList(data, message.getId());
        addByteToHexAsciiList(data, message.getCommand());
        if (message.getData() == null) {
            return data;
        }
        for (Integer value : message.getData()) {
            addByteToHexAsciiList(data, value);
        }
        return data;
    }

    /**
     * @param data -
     * @param position -
     * @return -
     * @throws DataConvertException -
     * @throws MessageException -
     */
    public static int getDoubleFromHexAsciiList(List<Integer> data, int position)
            throws DataConvertException, MessageException {
        try {
            int value = MessageConverter.getWordFromHexAsciiList(data, position) << 16;
            value |= MessageConverter.getWordFromHexAsciiList(data, position + 4);
            return value;
        } catch (IndexOutOfBoundsException e) {
            throw new MessageException("Data index out of bounds");
        }
    }

    /**
     * @param data -
     * @param position -
     * @return -
     * @throws DataConvertException -
     * @throws MessageException -
     */
    public static int getWordFromHexAsciiList(List<Integer> data, int position)
            throws DataConvertException, MessageException {
        try {
            int value = MessageConverter.getByteFromHexAsciiList(data, position) << 8;
            value |= MessageConverter.getByteFromHexAsciiList(data, position + 2);
            return value;
        } catch (IndexOutOfBoundsException e) {
            throw new MessageException("Data index out of bounds");
        }
    }

    /**
     * @param data -
     * @param position -
     * @return -
     * @throws DataConvertException -
     * @throws MessageException -
     */
    public static int getByteFromHexAsciiList(List<Integer> data, int position)
            throws DataConvertException, MessageException {
        try {
            int value = DataConverter.hexAsciiToDigit(data.get(position)) << 4;
            value |= DataConverter.hexAsciiToDigit(data.get(position + 1));
            return value;
        } catch (IndexOutOfBoundsException e) {
            throw new MessageException("Data index out of bounds");
        }
    }

    /**
     * @param data -
     * @param value -
     * @throws DataConvertException -
     */
    public static void addByteToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
        data.add(DataConverter.digitToHexAscii((value >> 4) & 0xFF));
        data.add(DataConverter.digitToHexAscii(value & 0x0F));
    }

    /**
     * @param data -
     * @param value -
     * @throws DataConvertException -
     */
    public static void addWordToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
        addByteToHexAsciiList(data, (value >> 8) & 0xFF);
        addByteToHexAsciiList(data, value & 0xFF);
    }

    /**
     * @param data -
     * @param value -
     * @throws DataConvertException -
     */
    public static void addDoubleToHexAsciiList(List<Integer> data, int value) throws DataConvertException {
        addWordToHexAsciiList(data, (value >> 16) & 0xFFFF);
        addWordToHexAsciiList(data, value & 0xFFFF);
    }

}
