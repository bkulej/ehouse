package pl.np.ehouse.core.message;

import java.util.ArrayList;
import java.util.List;

import pl.np.ehouse.core.utils.DataConvertException;
import pl.np.ehouse.core.utils.DataConverter;

/**
 * 
 * @author Bartek
 *
 */
public class MessageCoverter {

	/**
	 * 
	 * @param data
	 * @return
	 * @throws MessageException
	 * @throws DataConvertException
	 */
	public static Message listToMessage(List<Integer> data) throws MessageException, DataConvertException {
		MessageImpl message = new MessageImpl(data.get(0));
		if (message.isAddress()) {
			message.setAdd(DataConverter.getWord(data, 1));
			message.setAsd(DataConverter.getWord(data, 5));
		} else {
			message.setSerial(DataConverter.getDouble(data, 1));
		}
		message.setId(DataConverter.getByte(data, 9));
		message.setCommand(DataConverter.getByte(data, 11));
		for (int i = 13; i < data.size(); i += 2) {
			message.addData(DataConverter.getByte(data, i));
		}
		return message;
	}
	
	public static List<Integer> messageToList(Message message) {
		List<Integer> data = new ArrayList<Integer>();
		
		return data;
	}

}
