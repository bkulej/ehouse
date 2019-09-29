package pl.np.ehouse.core.event;

import org.springframework.context.ApplicationEvent;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public class MessageInEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Message messageIn;
	
	/**
	 * 
	 * @param source
	 * @param messageIn
	 */
	public MessageInEvent(Object source, Message messageIn) {
		super(source);
		this.messageIn = messageIn;
	}

	/**
	 * @return the messageIn
	 */
	public Message getMessageIn() {
		return messageIn;
	}

	@Override
	public String toString() {
		return "MessageInEvent [messageIn=" + messageIn + "]";
	}

}
