package pl.np.ehouse.serial.comm;

/**
 * 
 * @author Bartek
 *
 */
public class Crc8 {

	/**
	 * 
	 * @param crc
	 * @param value
	 * @return
	 */
	public static int update(int crc, int value) {
		crc ^= value;
		for (int i = 0; i < 8; i++) {
			if ((crc & 0x01) == 1) {
				crc = (crc >> 1) ^ 0x8C;
			} else {
				crc >>= 1;
			}
		}
		return crc;
	}

}
