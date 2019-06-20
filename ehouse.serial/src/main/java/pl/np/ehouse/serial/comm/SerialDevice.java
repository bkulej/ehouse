package pl.np.ehouse.serial.comm;

import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * @author Bartek
 */
@Service
public class SerialDevice {

	private final Logger log = LoggerFactory.getLogger(SerialDevice.class);
	private final String port;

	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	@Autowired
	SerialDevice(@Value("${serial.comm.port}") String port) {
		this.port = port;
	}

	/**
	 * @throws PortInUseException                -
	 * @throws NoSuchPortException               -
	 * @throws UnsupportedCommOperationException -
	 * @throws IOException                       -
	 */
	@PostConstruct
	public void open() throws PortInUseException, NoSuchPortException, UnsupportedCommOperationException, IOException {
		log.info("Opening serial on port {}", port);
		serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(port).open(this.getClass().getName(), 2000);
		serialPort.setSerialPortParams(SerialConst.BOUND, SerialConst.DATA_BITS, SerialConst.STOP_BITS,
				SerialConst.PARITY_EVEN);
		serialPort.notifyOnDataAvailable(true);
		serialPort.setOutputBufferSize(0);
		serialPort.setRTS(true);
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
	}

	/**
	 * @throws IOException -
	 */
	@PreDestroy
	public void close() throws IOException {
		log.info("Closing serial on port {}", port);
		if (inputStream != null) {
			inputStream.close();
		}
		if (outputStream != null) {
			outputStream.flush();
			outputStream.close();
		}
		if (serialPort != null) {
			serialPort.close();
		}
		log.info("Closed serial on port {}", port);
	}

	/**
	 * @throws IOException -
	 */
	void startSend() throws IOException {
		log.debug("Start sending message");
		serialPort.setRTS(false);
		outputStream.write(0);
		outputStream.flush();
	}

	/**
	 * @throws IOException -
	 */
	public void stopSend() {
		try {
			outputStream.write(0);
			outputStream.flush();
			serialPort.setRTS(true);
		} catch (IOException e) {
			log.error("Error during serial device stoping", e);
		}
		log.debug("Stop sending message");
	}

	/**
	 * @return -
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return -
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param listener -
	 * @throws TooManyListenersException -
	 */
	public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException {
		serialPort.addEventListener(listener);
	}

}
