package pl.np.ehouse.core.message;

public interface MessageSender {
	
	/**
     * @param message -
     */
    public void sendMessage(Message message);

    /**
     * @param message -
     */
    public void receivedResponse(Message message);

}
