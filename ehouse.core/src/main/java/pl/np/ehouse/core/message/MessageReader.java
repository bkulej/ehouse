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
public class MessageReader implements Runnable {

	private final Logger log = LoggerFactory.getLogger(MessageReader.class);

	@Autowired
	Connection connection;

	@Override
	public void run() {
		try {
			log.info("Start service {}", this.getClass());
			while (true) {
				connection.read();
			}
		} catch (Exception e) {
			log.error("MessageReader error {}", e);
		} finally {
			log.info("End of service {}", this.getClass());
		}
	}

}
