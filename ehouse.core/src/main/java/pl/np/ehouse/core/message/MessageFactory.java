package pl.np.ehouse.core.message;

import pl.np.ehouse.core.utils.DataConvertException;
import pl.np.ehouse.core.utils.DataConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartek
 */
public class MessageFactory {

    /**
     * @param data -
     * @return -
     * @throws MessageException     -
     * @throws DataConvertException -
     */
    public static Message fromList(List<Integer> data) throws MessageException, DataConvertException {
        MessageImpl message = new MessageImpl(data.get(0));
        if (message.isAddress()) {
            message.setAdd(DataConverter.getWordFromHexAsciiList(data, 1));
            message.setAsd(DataConverter.getWordFromHexAsciiList(data, 5));
        } else {
            message.setSerial(DataConverter.getDoubleFromHexAsciiList(data, 1));
        }
        message.setId(DataConverter.getByteFromHexAsciiList(data, 9));
        message.setCommand(DataConverter.getByteFromHexAsciiList(data, 11));
        for (int i = 13; i < data.size(); i += 2) {
            message.addData(DataConverter.getByteFromHexAsciiList(data, i));
        }
        return message;
    }

    /**
     * @param message -
     * @return -
     * @throws MessageException     -
     * @throws DataConvertException -
     */
    public static List<Integer> toList(Message message) throws DataConvertException, MessageException {
        List<Integer> data = new ArrayList<>();
        data.add(message.getType());
        if (message.isAddress()) {
            DataConverter.addWordToHexAsciiList(data, message.getAdd());
            DataConverter.addWordToHexAsciiList(data, message.getAsd());
        } else {
            DataConverter.addDoubleToHexAsciiList(data, message.getSerial());
        }
        DataConverter.addByteToHexAsciiList(data, message.getId());
        DataConverter.addByteToHexAsciiList(data, message.getCommand());
        if (message.getData() == null) {
            return data;
        }
        for (Integer value : message.getData()) {
            DataConverter.addByteToHexAsciiList(data, value);
        }
        return data;
    }

}
