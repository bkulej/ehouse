package pl.np.ehouse.serial.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * 
 * @author Bartek
 *
 */
@Configuration
public class SerialConfig {

	private final Logger log = LoggerFactory.getLogger(SerialConfig.class);

	/**
	 * 
	 * @param port
	 * @param timeout
	 * @return
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 * @throws UnsupportedCommOperationException
	 */
	@Bean(destroyMethod="close")
	SerialPort serialPort(@Value("${serial.comm.port}") String port)
			throws PortInUseException, NoSuchPortException, UnsupportedCommOperationException {
		log.info("Opening serial on port {}", port);
		final SerialPort serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(port)
				.open(this.getClass().getName(), 2000);
		serialPort.setSerialPortParams(SerialConst.BOUND, SerialConst.DATA_BITS, SerialConst.STOP_BITS,
				SerialConst.PARITY_EVEN);
		serialPort.notifyOnDataAvailable(true);
		serialPort.setOutputBufferSize(0);
		serialPort.setRTS(true);
		return serialPort;
	}

}
