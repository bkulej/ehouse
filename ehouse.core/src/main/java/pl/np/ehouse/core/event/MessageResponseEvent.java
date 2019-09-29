package pl.np.ehouse.core.event;

import org.springframework.context.ApplicationEvent;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public class MessageResponseEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Message messageOut;
	private final Message messageIn;

	/**
	 * 
	 * @param source
	 * @param messageOut
	 */
	public MessageResponseEvent(Object source, Message messageOut) {
		super(source);
		this.messageOut = messageOut;
		this.messageIn = null;
	}

	/**
	 * 
	 * @param source
	 * @param messageOut
	 * @param messageIn
	 */
	public MessageResponseEvent(Object source, Message messageOut, Message messageIn) {
		super(source);
		this.messageOut = messageOut;
		this.messageIn = messageIn;
	}

	/**
	 * @return the messageIn
	 */
	public Message getMessageIn() {
		return messageIn;
	}

	/**
	 * @return the messageOut
	 */
	public Message getMessageOut() {
		return messageOut;
	}

	@Override
	public String toString() {
		return "MessageResponseEvent [messageIn=" + messageIn + ", messageOut=" + messageOut + "]";
	}

}
