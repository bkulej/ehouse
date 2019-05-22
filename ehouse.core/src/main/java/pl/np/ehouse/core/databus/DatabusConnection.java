package pl.np.ehouse.core.databus;

import java.io.IOException;
import java.util.List;

/**
 * @author Bartek
 */
public interface DatabusConnection {

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
