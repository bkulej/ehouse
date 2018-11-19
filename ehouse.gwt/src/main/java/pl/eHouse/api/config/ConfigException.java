/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.config;

/**
 *
 * @author Bartek
 */
public class ConfigException extends Exception {
    
	private static final long serialVersionUID = 4268175136854573715L;

	public ConfigException(String mess) {
        super(mess);
    }
    
}
