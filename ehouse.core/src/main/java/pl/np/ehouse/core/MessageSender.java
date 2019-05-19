package pl.np.ehouse.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.np.ehouse.core.connection.Connection;
import pl.np.ehouse.core.message.Message;
import pl.np.ehouse.core.message.MessageConverter;

import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Bartek
 */
@Service
class MessageSender implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final Connection connection;
    private final LinkedBlockingQueue<Message> outputQueue;

    private volatile boolean started = true;

    /**
     * @param connection -
     */
    @Autowired
    public MessageSender(Connection connection) {
        this.connection = connection;
        this.outputQueue = new LinkedBlockingQueue<>();
    }

    /**
     * @param message -
     */
    public void sendMessage(Message message) {
        outputQueue.add(message);
    }

    @Override
    public void run() {
        try {
            log.info("Start service {}", this.getClass());
            while (started) {
                Message message = outputQueue.take();
                connection.send(MessageConverter.messageToList(message));
            }
        } catch (Exception e) {
            log.error("Error ", e);
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
