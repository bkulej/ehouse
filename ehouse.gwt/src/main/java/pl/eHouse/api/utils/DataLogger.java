package pl.eHouse.api.utils;


public class DataLogger {

	public synchronized static void log(int part) {

		if ((part & 0xF0) == 0x80) {
			// Start
			System.out.println();
			System.out.print(ConvertUtil.getStringDate() + " DataLogger <" + ConvertUtil.byteToHex(part) + " ");
		} else if ((part & 0xF0) == 0xB0) {
			// Stop
			System.out.println(ConvertUtil.byteToHex(part) + ">");
		} else {
			// Other
			System.out.print(ConvertUtil.byteToHex(part) + " ");
		}
		
	}

	public static void main(String[] args) {
		try {
			for(int i = 0; i< 200; ++ i) {
				log(i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
