package pl.np.ehouse.serial.comm;

import gnu.io.SerialPort;

/**
 * @author Bartek
 */
class SerialConst {

    final static int DATA_BITS = SerialPort.DATABITS_8;
    final static int PARITY_EVEN = SerialPort.PARITY_EVEN;
    final static int STOP_BITS = SerialPort.STOPBITS_1;
    final static int BOUND = 38400;

    final static int HEADER = 0x80;
    final static int DATA = 0x90;
    final static int FOOTERA = 0xA0;
    final static int FOOTERB = 0xB0;
    final static int ERROR = 0xF0;

}
