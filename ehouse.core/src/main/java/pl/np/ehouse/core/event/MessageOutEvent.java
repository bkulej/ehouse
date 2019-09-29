package pl.np.ehouse.core.event;

import org.springframework.context.ApplicationEvent;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public class MessageOutEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Message messageOut;

	/**
	 * 
	 * @param source
	 * @param request
	 */
	public MessageOutEvent(Object source, Message messageOut) {
		super(source);
		this.messageOut = messageOut;
	}

	/**
	 * @return the messageOut
	 */
	public Message getMessageOut() {
		return messageOut;
	}

	@Override
	public String toString() {
		return "MessageOutEvent [messageOut=" + messageOut + "]";
	}

}
