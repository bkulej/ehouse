/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.eHouse.api.bootloader;

/**
 *
 * @author Bartek
 */
public interface BootloaderListener {
    
    public void start(int maxProgres);
    public void progres(int curProgres);
    public void end(int result);    
    
}
