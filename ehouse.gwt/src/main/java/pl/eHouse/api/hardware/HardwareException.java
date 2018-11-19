/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.hardware;

/**
 *
 * @author Bartek
 */
public class HardwareException extends Exception {
   
	private static final long serialVersionUID = -5770678360316145164L;

	public HardwareException(String mess) {
        super(mess);
    }
    
}
