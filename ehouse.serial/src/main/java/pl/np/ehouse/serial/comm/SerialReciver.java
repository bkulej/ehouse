package pl.np.ehouse.serial.comm;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.np.ehouse.serial.network.NetworkWriter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * @author Bartek
 */
@Service
class SerialReciver implements SerialPortEventListener {

    private final Logger log = LoggerFactory.getLogger(SerialReciver.class);
    private final SerialDevice serialDevice;
    private final NetworkWriter networkWriter;

    private List<Integer> message;
    private int crcCalculated;
    private int crcReceived;

    @Autowired
    public SerialReciver(SerialDevice serialDevice, NetworkWriter networkWriter) {
        this.serialDevice = serialDevice;
        this.networkWriter = networkWriter;
    }

    /**
     * @throws TooManyListenersException -
     */
    @PostConstruct
    public void init() throws TooManyListenersException {
        serialDevice.addEventListener(this);
    }

    /**
     * @param event -
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            int data;
            while ((data = serialDevice.getInputStream().read()) > -1) {
                if (decodeData(data) == SerialConst.FOOTERB) {
                    writeMessageToNetwork();
                }
            }
        } catch (IOException e) {
            log.error("Error during reading serial device", e);
        }
    }

    /*
     *
     */
    private int decodeData(int data) {
        switch (data & 0xF0) {
            case SerialConst.HEADER:
                handleHeader(data);
                return SerialConst.HEADER;
            case SerialConst.FOOTERA:
                return handleFooterA(data);
            case SerialConst.FOOTERB:
                return handleFooterB(data);
            default:
                return handleData(data);
        }
    }

    /*
     *
     */
    private void handleHeader(int data) {
        message = new ArrayList<>();
        message.add(data & 0x0F);
        crcCalculated = Crc8.update(0, data);
    }

    /*
     *
     */
    private int handleData(int data) {
        if (message != null) {
            message.add(data);
            crcCalculated = Crc8.update(crcCalculated, data);
            return SerialConst.DATA;
        } else {
            log.debug("Unexpected data {}", data);
            return SerialConst.ERROR;
        }
    }

    /*
     *
     */
    private int handleFooterA(int data) {
        if (message != null) {
            crcReceived = (data & 0x0F) << 4;
            return SerialConst.FOOTERA;
        } else {
            log.debug("Unexpected end {}", data);
            return SerialConst.ERROR;
        }
    }

    /*
     *
     */
    private int handleFooterB(int data) {
        if (message != null) {
            crcReceived |= data & 0x0F;
            return crcReceived == crcCalculated ? SerialConst.FOOTERB : SerialConst.ERROR;
        } else {
            log.debug("Unexpected end {}", data);
            return SerialConst.ERROR;
        }
    }

    /*
     *
     */
    private void writeMessageToNetwork() {
        log.debug("Received message {}", message);
        networkWriter.write(message);
        message = null;
    }

}
