package pl.eHouse.api.hardware;

import pl.eHouse.api.utils.ConvertUtil;

public class DeviceTypes {
	
	public final static int TYPE_EMPTY = 0xFF;

	public final static int TYPE_SYSTEM = 0x00;
	public final static int TYPE_OUTPUT1 = 0x01;
	public final static int TYPE_OUTPUT2 = 0x02;
	public final static int TYPE_THERM = 0x03;
	public final static int TYPE_VOLT = 0x04;
	public final static int TYPE_INPUT = 0x05;
	public final static int TYPE_PWM = 0x06;
	public final static int TYPE_FREQ = 0x07;

	public final static int TYPE_HEAT_MAIN = 0x30;
	public final static int TYPE_HEAT_3WAY = 0x31;
	public final static int TYPE_HEAT_4WAY = 0x32;
	public final static int TYPE_IRRIGATION = 0x33;
	public final static int TYPE_RECUPERATOR = 0x34;
	public final static int TYPE_GATE1 = 0x35;
	public final static int TYPE_GATE2 = 0x36;

	public static String getName(int type) {
		switch(type) {
			case 0x00: return "SYSTEM";
			case 0x01: return "OUTPUT 1";
			case 0x02: return "OUTPUT 2";
			case 0x03: return "THERMOMETER";
			case 0x04: return "VOLTMETER";	
			case 0x05: return "INPUT";
			case 0x06: return "PWM";
			case 0x07: return "FREQ";
			
			case 0x20: return "TEST_MAIN";
			
			case 0x30: return "HEAT_MAIN";
			case 0x31: return "HEAT_3WAY";
			case 0x32: return "HEAT_4WAY";
			case 0x33: return "IRRIGATION";
			case 0x34: return "RECUPERATOR";
			case 0x35: return "GATE1";
			case 0x36: return "GATE2";
			
			default : return ConvertUtil.byteToHex(type);
		}	
	}

}
