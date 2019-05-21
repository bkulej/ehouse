package pl.np.ehouse.core.message;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.np.ehouse.core.connection.Connection;
import pl.np.ehouse.core.message.utils.Addresses;

/**
 * @author Bartek
 */
@Service
public class MessageDecoder implements Runnable {

    private final Logger log = LoggerFactory.getLogger(MessageDecoder.class);
    private final Connection connection;
    private final MessageSender sender;

    private volatile boolean started = true;

    /**
     * @param connection -
     */
    @Autowired
    public MessageDecoder(Connection connection, MessageSender sender) {
        this.connection = connection;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            log.info("Start service {}", this.getClass());
            while (started) {
                Message message = MessageFactory.fromList(connection.read());
                decode(message);
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

    /*
     * 
     */
    private void decode(Message message) throws MessageException {
        removeMessageFromSender(message);
    }

    /*
     * 
     */
    private void removeMessageFromSender(Message message) throws MessageException {
        if (message.isAddress() && message.getAdd() == Addresses.ADDRESS_COMPUTER) {
            sender.receivedResponse(message);
        } else if (message.isSerial() && message.getSerial() != Addresses.SERIAL_BROADCAST) {
            sender.receivedResponse(message);
        } else if (message.isBoot() && message.getSerial() != Addresses.SERIAL_BROADCAST) {
            sender.receivedResponse(message);
        }
    }

}
