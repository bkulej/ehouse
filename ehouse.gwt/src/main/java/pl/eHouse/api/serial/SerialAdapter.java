package pl.eHouse.api.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import pl.eHouse.api.listeners.ListenerException;

/**
 * 
 * @author Bartek
 */
public class SerialAdapter {

	private Logger log = Logger.getLogger(SerialAdapter.class.getName());
	private BlockingQueue<Integer> queue;

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStrem;

	public SerialAdapter(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void connect(String address, int port) throws ListenerException {
		try {
			log.info("Connecting to " + address + ":" + port);
			socket = new Socket(address, port);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStrem = new ObjectInputStream(socket.getInputStream());
			new SerialReceiver(queue, inputStrem).start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage());
		}
	};

	public void disconnect() throws ListenerException {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ListenerException(e.getMessage());
		}
	}

	public void send(int type, List<Integer> data) {
		try {
			List<Integer> message = new ArrayList<>();
			message.add(type);
			data.forEach(value -> message.add(value));
			outputStream.writeObject(message);
			log.fine("Send message " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
