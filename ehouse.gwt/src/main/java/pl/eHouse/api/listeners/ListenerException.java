/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.listeners;

/**
 *
 * @author Bartek
 */
public class ListenerException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2990303265248754630L;

	/**
     * Konstruktor
     * @param description
     */
    public ListenerException(String description) {
        super(description);
    }

}
