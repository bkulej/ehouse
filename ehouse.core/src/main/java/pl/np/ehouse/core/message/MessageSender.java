package pl.np.ehouse.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.connection.Connection;

/**
 * 
 * @author Bartek
 *
 */
@Service
public class MessageSender implements Runnable {

	private final Logger log = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	Connection connection;

	@Override
	public void run() {
		try {
			log.info("Start service {}", this.getClass());
		} finally {
			log.info("End of service {}", this.getClass());
		}
	}

}
