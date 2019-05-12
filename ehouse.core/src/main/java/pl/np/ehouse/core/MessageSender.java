package pl.np.ehouse.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.connection.Connection;
import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class MessageSender implements Runnable {

	private final Logger log = LoggerFactory.getLogger(MessageSender.class);

	private LinkedBlockingQueue<Message> outputQueue;

	@Autowired
	Connection connection;

	/**
	 * 
	 * @param message
	 */
	public void sendMessage(Message message) {
		outputQueue.add(message);
	}

	@Override
	public void run() {
		try {
			log.info("Start service {}", this.getClass());
			while (true) {
				Message message = outputQueue.take();
			}
		} catch (Exception e) {
			log.error("Error ", e);
		} finally {
			log.info("End of service {}", this.getClass());
		}
	}

}
