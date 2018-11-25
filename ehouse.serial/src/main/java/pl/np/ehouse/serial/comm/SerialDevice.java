package pl.np.ehouse.serial.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class SerialDevice {

	private final static int DATA_BITS = SerialPort.DATABITS_8;
	private final static int PARITY_EVEN = SerialPort.PARITY_EVEN;
	private final static int STOP_BITS = SerialPort.STOPBITS_1;
	private final static int BOUND = 38400;

	private final Logger log = LoggerFactory.getLogger(SerialDevice.class);

	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	@Value("${serial.comm.port}")
	private String port;

	/**
	 * 
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 * @throws UnsupportedCommOperationException
	 * @throws IOException
	 * @throws TooManyListenersException
	 */
	@PostConstruct
	public void open() throws PortInUseException, NoSuchPortException, UnsupportedCommOperationException, IOException,
			TooManyListenersException {
		log.info("Opening serial on port {}", port);
		serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(port).open(this.getClass().getName(), 2000);
		serialPort.setSerialPortParams(BOUND, DATA_BITS, STOP_BITS, PARITY_EVEN);
		serialPort.notifyOnDataAvailable(true);
		serialPort.setOutputBufferSize(0);
		serialPort.setRTS(true);
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
	}

	/**
	 * @throws IOException 
	 * 
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
	 * 
	 * @throws IOException
	 */
	public void startSend() throws IOException {
		log.debug("Start sending message");
		serialPort.setRTS(false);
		outputStream.write(0);
		outputStream.flush();
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void stopSend() throws IOException {
		outputStream.write(0);
		outputStream.flush();
		serialPort.setRTS(true);
		log.debug("Stop sending message");
	}

	/**
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @return
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param listener
	 * @throws TooManyListenersException
	 */
	public void addEventListener(SerialPortEventListener listener) throws TooManyListenersException {
		serialPort.addEventListener(listener);
	}

}
