package pl.np.ehouse.core.event;

import pl.np.ehouse.core.message.Message;

/**
 * 
 * @author bkulejewski
 *
 */
public interface MessageEvent {
	
	/**
	 * 
	 * @return
	 */
	public Message getMessage();

}
