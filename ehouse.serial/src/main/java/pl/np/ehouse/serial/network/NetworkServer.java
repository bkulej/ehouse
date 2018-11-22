package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
 * @author bkulejewski
 *
 */
@Service
public class NetworkServer {
	
	private final Logger log = LoggerFactory.getLogger(NetworkServer.class);

	@Value("${serial.network.port}")
	private Integer port;

	@Autowired
	private NetworkReader networkReader;

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void start() throws IOException, ClassNotFoundException {
		log.info("Opening socket on port {}", port);
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (true) {
				networkReader.start(serverSocket.accept());
			}
		}
	}

}