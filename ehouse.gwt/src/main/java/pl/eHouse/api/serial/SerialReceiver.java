package pl.eHouse.api.serial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import pl.eHouse.api.message.Header;

public class SerialReceiver extends Thread {

	private Logger log = Logger.getLogger(SerialReceiver.class.getName());

	private BlockingQueue<Integer> queue;
	private ObjectInputStream inputStream;

	public SerialReceiver(BlockingQueue<Integer> queue, ObjectInputStream inputStream) throws IOException {
		super();
		this.queue = queue;
		this.inputStream = inputStream;
	}

	public void run() {
		try {
			while (true) {
				writeToQueue(inputStream.readObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void writeToQueue(Object object) throws InterruptedException {
		if (object instanceof List) {
			List<Integer> message = (List<Integer>) object;
			log.fine("Received message " + message);
			boolean isType = true;
			for (Integer data : message) {
				if (isType) {
					data = Header.HEADER | data;
					isType = false;
				}
				queue.put(data);
			}
			queue.put(Header.FOOTER1);
			queue.put(Header.FOOTER2);
		}
	}

}
