package pl.np.ehouse.core.event;

import org.springframework.context.ApplicationEvent;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public class MessageInEvent extends ApplicationEvent implements MessageEvent {

	private static final long serialVersionUID = 1L;

	private final Message message;

	/**
	 * 
	 * @param source
	 * @param message
	 */
	public MessageInEvent(Object source, Message message) {
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
		return "MessageInEvent [message=" + message + "]";
	}

}
