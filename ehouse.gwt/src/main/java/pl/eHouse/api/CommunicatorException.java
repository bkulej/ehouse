/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api;

/**
 *
 * @author Bartek
 */
public class CommunicatorException extends Exception {

	private static final long serialVersionUID = 7611811545878103442L;

	/**
     * Konstruktor
     * @param message
     */
    public CommunicatorException (String message) {
        super(message);
    }
    
}
