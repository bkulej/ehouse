package pl.np.ehouse.core.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class SocketConnection implements Connection, Runnable {

	@Value("${core.connection.socket.host}")
	private String host;

	@Value("${core.connection.socket.port}")
	private int port;

	private final Logger log = LoggerFactory.getLogger(SocketConnection.class);
	private final BlockingQueue<List<Integer>> inputQueue = new LinkedBlockingQueue<List<Integer>>();

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	@PostConstruct
	public void open() throws UnknownHostException, IOException {
		log.info("Opening socket connection on {}:{}", host, port);
		socket = new Socket(host, port);
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
	}

	@PreDestroy
	public void close() throws IOException {
		log.info("Closing socket connection on {}:{}", host, port);
		if (socket != null) {
			socket.close();
		}
	}

	/**
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void send(List<Integer> message) throws IOException {
		outputStream.writeObject(message);
		outputStream.flush();
	}
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public List<Integer> read() throws InterruptedException {
		return inputQueue.take();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			log.info("Start thread {}", this.getClass());
			while (true) {
				Object message = inputStream.readObject();
				if (message instanceof List) {
					inputQueue.put((List<Integer>) message);
				}
			}
		} catch (Exception e) {
			log.info("{}", e.getMessage());
		} finally {
			log.info("End of thread {}", this.getClass());
		}
	}

}
