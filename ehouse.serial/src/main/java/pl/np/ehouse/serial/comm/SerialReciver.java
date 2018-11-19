package pl.np.ehouse.serial.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import pl.np.ehouse.serial.network.NetworkWriter;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class SerialReciver implements SerialPortEventListener {

	private final static int HEADER = 0x80;
	private final static int DATA = 0x90;
	private final static int FOOTERA = 0xA0;
	private final static int FOOTERB = 0xB0;
	private final static int ERROR = 0xF0;

	private final static Logger log = LoggerFactory.getLogger(SerialReciver.class);

	private final List<Integer> message = new ArrayList<>();
	private int crcCalculated;
	private int crcReceived;

	@Autowired
	SerialDevice serialDevice;
	
	@Autowired
	NetworkWriter networkWriter;

	/**
	 * 
	 * @throws TooManyListenersException
	 */
	@PostConstruct
	public void init() throws TooManyListenersException {
		serialDevice.addEventListener(this);
	}

	/**
	 * 
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		try {
			int data;
			while ((data = serialDevice.getInputStream().read()) > -1) {
				if (decodeData(data) == FOOTERB) {
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
		case HEADER:
			return handleHeader(data);
		case FOOTERA:
			return handleFooterA(data);
		case FOOTERB:
			return handleFooterB(data);
		default:
			return handleData(data);
		}
	}

	/*
	 * 
	 */
	private int handleHeader(int data) {
		message.clear();
		message.add(data & 0x0F);
		crcCalculated = Crc8.update(0, data);
		return HEADER;
	}

	/*
	 * 
	 */
	private int handleData(int data) {
		if (message.size() != 0) {
			message.add(data);
			crcCalculated = Crc8.update(crcCalculated, data);
			return DATA;
		} else {
			log.error("Unexpected data {}", data);
			message.clear();
			return ERROR;
		}
	}

	/*
	 * 
	 */
	private int handleFooterA(int data) {
		if (message.size() != 0) {
			crcReceived = (data & 0x0F) << 4;
			return FOOTERA;
		} else {
			log.error("Unexpected end {}", data);
			message.clear();
			return ERROR;
		}
	}

	/*
	 * 
	 */
	private int handleFooterB(int data) {
		if (message.size() != 0) {
			crcReceived |= data & 0x0F;
			return crcReceived == crcCalculated ? FOOTERB : ERROR;
		} else {
			log.error("Unexpected end {}", data);
			message.clear();
			return ERROR;
		}
	}

	/*
	 * 
	 */
	private void writeMessageToNetwork() {
		log.debug("Received message {}", message);
		networkWriter.write(message);
		message.clear();
	}

}
