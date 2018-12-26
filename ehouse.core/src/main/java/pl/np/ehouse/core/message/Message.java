package pl.np.ehouse.core.message;

import java.util.List;

/**
 * 
 * @author Bartek
 *
 */
public interface Message {
	
	/**
	 * 
	 * @return
	 */
	public int getType();

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public int getSerial() throws MessageException;

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public int getAsd() throws MessageException;

	/**
	 * 
	 * @return
	 * @throws MessageException
	 */
	public int getAdd() throws MessageException;

	/**
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * 
	 * @return
	 */
	public int getCommand();

	/**
	 * 
	 * @return
	 */
	public List<Integer> getData();
	
	/**
	 * 
	 * @return
	 */
	public boolean isSerial();

	/**
	 * 
	 * @return
	 */
	public boolean isAddress();

	/**
	 * 
	 * @return
	 */
	public boolean isBoot();

}
