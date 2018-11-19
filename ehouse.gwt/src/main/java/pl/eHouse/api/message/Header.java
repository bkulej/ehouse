/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.message;

/**
 *
 * @author Bartek
 */
public class Header {

    // Rozklad bitow
    public final static int TYPE_BITS = 0x0F;
    public final static int HANDF_BITS = 0xF0;
    public final static int CRC_BITS = 0x0F;
    // Typ adresowania
    public final static int SERIAL = 0x00;
    public final static int ADDRESS = 0x01;
    public final static int BOOT = 0x02;
    
    // Nagowwek i stopka
    public final static int HEADER = 0x80;
    public final static int FOOTER1 = 0xA0;    
    public final static int FOOTER2 = 0xB0;

}
