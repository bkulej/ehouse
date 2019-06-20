package pl.np.ehouse.serial.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bartek
 */
@Service
public class SerialWriter {

	private final Logger log = LoggerFactory.getLogger(SerialWriter.class);
	private final SerialDevice serialDevice;

	@Autowired
	public SerialWriter(SerialDevice serialDevice) {
		this.serialDevice = serialDevice;
	}

	/**
	 * @param message -
	 */
	public synchronized void write(List<Integer> message) {
		try {
			serialDevice.startSend();
			final var outputStream = serialDevice.getOutputStream();
			for (var value : getMessageBytes(message)) {
				outputStream.write(value);
			}
			outputStream.flush();
		} catch (IOException e) {
			log.error("Error during writing to serial device", e);
		} finally {
			serialDevice.stopSend();
		}
	}

	/*
	 *
	 */
	private List<Integer> getMessageBytes(List<Integer> messageInput) {
		log.debug("Message to send {}", messageInput);
		final var messageOutput = new ArrayList<Integer>();
		var crc = 0;
		var isHeader = true;
		for (var value : messageInput) {
			if (isHeader) {
				value = SerialConst.HEADER | value;
				isHeader = false;
			}
			crc = Crc8.update(crc, value);
			messageOutput.add(value);
		}
		messageOutput.add(SerialConst.FOOTERA | ((crc >> 4) & 0x0F));
		messageOutput.add(SerialConst.FOOTERB | (crc & 0x0F));
		log.debug("Message sended {}", messageOutput);
		return messageOutput;
	}

}
