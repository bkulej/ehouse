package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NetworkWriter {
	
	private Logger logger = LoggerFactory.getLogger(NetworkWriter.class);

	private Map<String, ObjectOutputStream> socketStreams = new ConcurrentHashMap<>();

	/**
	 * 
	 * @param socket
	 * @param outputStream
	 */
	public void addSocket(Socket socket, ObjectOutputStream outputStream) {
		logger.debug("Add {}" , socket);
		socketStreams.put(socket.toString(), outputStream);
	}

	/**
	 * 
	 * @param socket
	 */
	public void removeSocket(Socket socket) {
		logger.debug("Remove {}" , socket);
		socketStreams.remove(socket.toString());
	}

	/**
	 * 
	 * @param message
	 */
	public void write(List<Integer> message) {
		socketStreams.forEach((key, value) -> {
			try {
				value.writeObject(message);
				value.flush();
			} catch (IOException e) {
			}
		});
	}

}
