package pl.np.ehouse.core.message;

import java.util.List;

/**
 * @author Bartek
 */
public interface Message {

    /**
     * @return -
     */
    int getType();

    /**
     * @return -
     * @throws MessageException -
     */
    int getSerial() throws MessageException;

    /**
     * @return -
     * @throws MessageException -
     */
    int getAsd() throws MessageException;

    /**
     * @return -
     * @throws MessageException -
     */
    int getAdd() throws MessageException;

    /**
     * @return -
     */
    int getId();

    /**
     * @return -
     */
    int getCommand();

    /**
     * @return -
     */
    List<Integer> getData();

    /**
     * @return -
     */
    boolean isSerial();

    /**
     * @return -
     */
    boolean isAddress();

    /**
     * @return -
     */
    boolean isBoot();

}
