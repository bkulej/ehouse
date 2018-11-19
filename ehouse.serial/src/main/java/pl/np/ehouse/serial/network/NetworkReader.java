package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pl.np.ehouse.serial.comm.SerialWriter;

@Service
public class NetworkReader {

	private final Logger log = LoggerFactory.getLogger(NetworkReader.class);

	@Autowired
	NetworkWriter networkWriter;
	
	@Autowired
	SerialWriter serialWriter;

	/**
	 * 
	 * @param socket
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Async
	public void start(Socket socket) throws IOException, ClassNotFoundException {
		try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());) {
			networkWriter.addSocket(socket, outputStream);
			while (true) {
				writeToSerial(socket, input.readObject());
			}
		} catch (IOException e) {
		} finally {
			networkWriter.removeSocket(socket);
			log.debug("Close socket {}", socket);
			if (!socket.isClosed()) {
				socket.close();
			}
		}
	}

	/*
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void writeToSerial(Socket socket, Object message) {
		if (message instanceof List) {
			serialWriter.write((List<Integer>) message);
		}
	}

}
