package pl.np.ehouse.serial.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartek
 */
@Service
public class SerialWriter {

    private final static int HEADER = 0x80;
    private final static int FOOTERA = 0xA0;
    private final static int FOOTERB = 0xB0;

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
            OutputStream outputStream = serialDevice.getOutputStream();
            for (Integer value : prepareMessage(message)) {
                outputStream.write(value);
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("Error during writing to serial device", e);
        } finally {
            try {
                serialDevice.stopSend();
            } catch (IOException e) {
                log.error("Error during serial device stoping", e);
            }
        }
    }

    /*
     *
     */
    private List<Integer> prepareMessage(List<Integer> messageInput) {
        log.debug("Message to send {}", messageInput);
        List<Integer> messageOutput = new ArrayList<>();
        int crc = 0;
        boolean isHeader = true;
        for (int value : messageInput) {
            if (isHeader) {
                value = HEADER | value;
                isHeader = false;
            }
            crc = Crc8.update(crc, value);
            messageOutput.add(value);
        }
        messageOutput.add(FOOTERA | ((crc >> 4) & 0x0F));
        messageOutput.add(FOOTERB | (crc & 0x0F));
        log.debug("Message sended {}", messageOutput);
        return messageOutput;
    }

}
