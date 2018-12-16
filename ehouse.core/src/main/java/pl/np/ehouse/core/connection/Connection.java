package pl.np.ehouse.core.connection;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Bartek
 *
 */
public interface Connection {
	
	/**
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void send(List<Integer> message) throws IOException;
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public List<Integer> read() throws InterruptedException;

}
