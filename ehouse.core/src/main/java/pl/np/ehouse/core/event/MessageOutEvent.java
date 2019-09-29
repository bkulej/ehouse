package pl.np.ehouse.core.event;

import org.springframework.context.ApplicationEvent;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public class MessageOutEvent extends ApplicationEvent implements MessageEvent {

	private static final long serialVersionUID = 1L;

	private final Message message;

	/**
	 * 
	 * @param source
	 * @param message
	 */
	public MessageOutEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}

	/**
	 * @return the messageOut
	 */
	public Message getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "MessageOutEvent [message=" + message + "]";
	}

}
