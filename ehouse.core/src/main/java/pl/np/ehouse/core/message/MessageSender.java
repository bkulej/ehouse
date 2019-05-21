package pl.np.ehouse.core.message;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.connection.Connection;
import pl.np.ehouse.core.message.utils.DataConvertException;

/**
 * @author Bartek
 */
@Service
public class MessageSender implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final Connection connection;
    private final BlockingQueue<Message> outputQueue;

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

    /**
     * @param message -
     */
    public void receivedResponse(Message message) {
       
        
    }

    @Override
    public void run() {
        try {
            log.info("Start service {}", this.getClass());
            while (started) {
                Message message = outputQueue.take();
                send(message);
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

    /*
     * 
     */
    private void send(Message message) throws IOException, DataConvertException, MessageException {
        connection.send(MessageFactory.toList(message));
    }

}
