package pl.np.ehouse.core.message;

/**
 * @author Bartek
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param message -
     */
    public MessageException(String message) {
        super(message);
    }

}
