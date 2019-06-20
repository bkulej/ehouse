package pl.np.ehouse.serial.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bkulejewski
 */
@Service
public class NetworkWriter {

	private final Logger log = LoggerFactory.getLogger(NetworkWriter.class);
	private final Map<String, ObjectOutputStream> socketStreams;

	/**
	 *
	 */
	public NetworkWriter() {
		socketStreams = new ConcurrentHashMap<>();
	}

	/**
	 * @param socket       -
	 * @param outputStream -
	 */
	public void addSocket(Socket socket, ObjectOutputStream outputStream) {
		log.info("Add connection {}", socket);
		socketStreams.put(socket.toString(), outputStream);
	}

	/**
	 * @param socket -
	 */
	public void removeSocket(Socket socket) {
		log.info("Remove connection {}", socket);
		socketStreams.remove(socket.toString());
	}

	/**
	 * @param message -
	 */
	public void write(List<Integer> message) {
		socketStreams.forEach((key, outputStream) -> write(outputStream, message));
	}

	/**
	 * 
	 * @param outputStream -
	 * @param message      -
	 */
	public void write(ObjectOutputStream outputStream, List<Integer> message) {
		try {
			outputStream.writeObject(message);
			outputStream.flush();
		} catch (IOException e) {
			log.error("Exception durring writing to output stream", e);
		}
	}

}
