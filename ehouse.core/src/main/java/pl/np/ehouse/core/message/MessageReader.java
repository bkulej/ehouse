package pl.np.ehouse.core.message;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.databus.DatabusConnection;
import pl.np.ehouse.core.event.MessageInEvent;
import pl.np.ehouse.core.event.MessageRequestEvent;

/**
 * @author Bartek
 */
@Service
public class MessageReader implements Runnable {

	private final Logger log = LoggerFactory.getLogger(MessageReader.class);
	private final DatabusConnection connection;
	private final ApplicationEventPublisher eventPublisher;

	private volatile boolean started = true;

	/**
	 * @param connection -
	 */
	@Autowired
	public MessageReader(DatabusConnection connection, ApplicationEventPublisher eventPublisher) {
		this.connection = connection;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			log.info("Start service {}", this.getClass());
			while (started) {
				Message message = MessageFactory.fromList(connection.read());
				log.debug("Received message {}", message);
				eventPublisher.publishEvent(new MessageInEvent(this, message));
				if (Messages.isRequestToServer(message)) {
					log.debug("Received message is request to server{}", message);
					eventPublisher.publishEvent(new MessageRequestEvent(this, message));
				}
			}
		} catch (Exception e) {
			log.error("MessageReader error", e);
		} finally {
			log.info("End of service {}", this.getClass());
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
