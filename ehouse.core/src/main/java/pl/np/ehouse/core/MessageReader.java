package pl.np.ehouse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.np.ehouse.core.connection.Connection;

import javax.annotation.PreDestroy;

/**
 * @author Bartek
 */
@Service
class MessageReader implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MessageReader.class);
    private final Connection connection;

    private volatile boolean started = true;

    /**
     * @param connection -
     */
    @Autowired
    public MessageReader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            log.info("Start service {}", this.getClass());
            while (started) {
                connection.read();
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
