package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.net.ServerSocket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 
 * @author bkulejewski
 *
 */
@Service
public class NetworkServer implements ApplicationRunner {

	private final Logger log = LoggerFactory.getLogger(NetworkServer.class);

	@Value("${serial.network.port}")
	private Integer port;

	@Autowired
	private NetworkReader networkReader;

	private ServerSocket serverSocket;

	/**
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void open() throws IOException {
		log.info("Opening socket on port {}", port);
		serverSocket = new ServerSocket(port);
	}

	/**
	 * 
	 */
	@Override
	public void run(ApplicationArguments args) throws ClassNotFoundException, IOException {
		while (true) {
			log.info("Wait for connection on port {}", port);
			networkReader.start(serverSocket.accept());
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	@PreDestroy
	public void close() throws IOException {
		log.info("Closing socket on port {}", port);
		if (serverSocket != null && serverSocket.isClosed()) {
			serverSocket.close();
		}
		log.info("Closed socket on port {}", port);
	}

}