package pl.np.ehouse.serial.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NetworkServer {
	
	private Logger logger = LoggerFactory.getLogger(NetworkServer.class);

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
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			while (true) {
				Socket socket = serverSocket.accept();
				logger.debug("Accepted {}", socket);
				networkReader.start(socket);
			}
		}
	}

}