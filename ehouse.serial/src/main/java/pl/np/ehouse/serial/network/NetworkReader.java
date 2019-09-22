package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pl.np.ehouse.serial.comm.SerialWriter;

/**
 * @author bkulejewski
 */
@Service
class NetworkReader {

	private final Logger log = LoggerFactory.getLogger(NetworkReader.class);
	private final NetworkWriter networkWriter;
	private final SerialWriter serialWriter;

	private volatile boolean started = true;

	@Autowired
	public NetworkReader(NetworkWriter networkWriter, SerialWriter serialWriter) {
		this.networkWriter = networkWriter;
		this.serialWriter = serialWriter;
	}

	/**
	 * @param socket -
	 * @throws IOException            -
	 * @throws ClassNotFoundException -
	 */
	@Async
	public void start(Socket socket) throws IOException, ClassNotFoundException {
		try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
			log.info("Accepted connection {}", socket);
			networkWriter.addSocket(socket, outputStream);
			while (started) {
				writeToSerial(inputStream.readObject());
			}
		} catch (IOException e) {
			log.info("Closing connection bye exception{}", socket);
		} finally {
			networkWriter.removeSocket(socket);
			log.info("Closing connection {}", socket);
			if (!socket.isClosed()) {
				socket.close();
			}
			log.info("Closed connection {}", socket);
		}
	}

	/**
	 *
	 */
	@PreDestroy
	public void finish() {
		started = false;
	}

	/*
	 */
	private void writeToSerial(Object object) {
		if (object instanceof List) {
			@SuppressWarnings("unchecked")
			List<Integer> list = (List<Integer>) object;
			serialWriter.write(list);
		}
	}

}
