package pl.np.ehouse.core.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.event.MessageEvent;
import pl.np.ehouse.core.event.MessageInEvent;
import pl.np.ehouse.core.event.MessageOutEvent;
import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
@Service
public class MessageLogger implements Runnable {

	private final Logger log = LoggerFactory.getLogger(MessageLogger.class);

	private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
	private volatile boolean started = true;

	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			log.info("Start service {}", this.getClass());
			while (started) {
				Message message = messages.take();
				log.debug("Register message {}", message);
			}
		} catch (Exception e) {
			log.error("MessageLogger error", e);
		} finally {
			log.info("End of service {}", this.getClass());
		}
	}

	/**
	 * 
	 * @param event
	 */
	@EventListener({ MessageInEvent.class, MessageOutEvent.class })
	public void logMessage(MessageEvent event) {
		try {
			messages.put(event.getMessage());
		} catch (InterruptedException e) {
			log.error("MessageLogger error", e);
		}
	}

	/**
	 *
	 */
	@PreDestroy
	public void finish() {
		started = false;
	}

}
