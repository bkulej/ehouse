package pl.np.ehouse.core.databus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Bartek
 */
@Service
public class SocketConnection implements DatabusConnection, Runnable {

    private final Logger log = LoggerFactory.getLogger(SocketConnection.class);
    private final BlockingQueue<List<Integer>> inputQueue = new LinkedBlockingQueue<>();
    private final String host;
    private final int port;

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private volatile boolean started = true;

    /**
     * @param host -
     * @param port -
     */
    @Autowired
    public SocketConnection(@Value("${core.connection.socket.host}") String host,
                            @Value("${core.connection.socket.port}") int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * @throws IOException -
     */
    @PostConstruct
    public void open() throws IOException {
        log.info("Opening socket connection on {}:{}", host, port);
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * @throws IOException -
     */
    @PreDestroy
    public void close() throws IOException {
        log.info("Closing socket connection on {}:{}", host, port);
        started = false;
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * @param message -
     * @throws IOException -
     */
    public void send(List<Integer> message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * @return -
     * @throws InterruptedException -
     */
    public List<Integer> read() throws InterruptedException {
        return inputQueue.take();
    }


    /*
     */
    @Override
    public void run() {
        try {
            log.info("Start service {}", this.getClass());
            while (started) {
                putMessageToQueue(inputStream.readObject());
            }
        } catch (Exception e) {
            log.error("Error durring reading object", e);
        } finally {
            log.info("End of service {}", this.getClass());
        }
    }

    /*
     */
    private void putMessageToQueue(Object object) throws InterruptedException {
        if (object instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Integer> message = (List<Integer>) object;
            inputQueue.put(message);
        }
    }

}
