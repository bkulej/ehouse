package pl.np.ehouse.core.connection;

import java.io.IOException;
import java.util.List;

/**
 * @author Bartek
 */
@SuppressWarnings("ALL")
public interface Connection {

    /**
     * @param message -
     * @throws IOException -
     */
    void send(List<Integer> message) throws IOException;

    /**
     * @return -
     * @throws InterruptedException -
     */
    List<Integer> read() throws InterruptedException;

}
