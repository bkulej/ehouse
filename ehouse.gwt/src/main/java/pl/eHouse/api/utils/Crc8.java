/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.eHouse.api.utils;

/**
 * 
 * @author Bartek
 */
public class Crc8 {

	private int crc;

	/**
	 * Konstruktor
	 */
	public Crc8() {
		crc = 0;
	}

	/**
	 * Wyczyszczenie sumy kontrolnej;
	 */
	public void clearCrc() {
		crc = 0;
	}

	/**
	 * Obliczenie sumy kontrolnej
	 * 
	 * @param crc
	 * @param value
	 * @return
	 */
	public void updateCrc(int value) {
		crc ^= value;
		for (int i = 0; i < 8; i++) {
			if ((crc & 0x01) == 1) {
				crc = (crc >> 1) ^ 0x8C;
			} else {
				crc >>= 1;
			}
		}
	}

	/*
	 * public void updateCrc(int value) { crc ^= value; int i; for (i = 0; i <
	 * 8; i++) { crc = ((crc << 1) ^ (((crc & 0x80) != 0) ? 0x07 : 0)) & 0xFF ;
	 * } }
	 */

	/**
	 * Pobranie koncowego crc
	 * 
	 * @return
	 */
	public int getCrc() {
		return crc;
	}

}
