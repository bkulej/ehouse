package pl.np.ehouse.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.np.ehouse.core.utils.DataConvertException;
import pl.np.ehouse.core.utils.DataConverter;

/**
 * 
 * @author Bartek
 *
 */
class DataConverterTest {

	@Test
	void testCorrectHexAsciiToDigit() {
		try {
			Assertions.assertEquals(0x00, DataConverter.hexAsciiToDigit('0'));
			Assertions.assertEquals(0x01, DataConverter.hexAsciiToDigit('1'));
			Assertions.assertEquals(0x02, DataConverter.hexAsciiToDigit('2'));
			Assertions.assertEquals(0x03, DataConverter.hexAsciiToDigit('3'));
			Assertions.assertEquals(0x04, DataConverter.hexAsciiToDigit('4'));
			Assertions.assertEquals(0x05, DataConverter.hexAsciiToDigit('5'));
			Assertions.assertEquals(0x06, DataConverter.hexAsciiToDigit('6'));
			Assertions.assertEquals(0x07, DataConverter.hexAsciiToDigit('7'));
			Assertions.assertEquals(0x08, DataConverter.hexAsciiToDigit('8'));
			Assertions.assertEquals(0x09, DataConverter.hexAsciiToDigit('9'));
			Assertions.assertEquals(0x0A, DataConverter.hexAsciiToDigit('A'));
			Assertions.assertEquals(0x0B, DataConverter.hexAsciiToDigit('B'));
			Assertions.assertEquals(0x0C, DataConverter.hexAsciiToDigit('C'));
			Assertions.assertEquals(0x0D, DataConverter.hexAsciiToDigit('D'));
			Assertions.assertEquals(0x0E, DataConverter.hexAsciiToDigit('E'));
			Assertions.assertEquals(0x0F, DataConverter.hexAsciiToDigit('F'));
			Assertions.assertEquals(0x0A, DataConverter.hexAsciiToDigit('a'));
			Assertions.assertEquals(0x0B, DataConverter.hexAsciiToDigit('b'));
			Assertions.assertEquals(0x0C, DataConverter.hexAsciiToDigit('c'));
			Assertions.assertEquals(0x0D, DataConverter.hexAsciiToDigit('d'));
			Assertions.assertEquals(0x0E, DataConverter.hexAsciiToDigit('e'));
			Assertions.assertEquals(0x0F, DataConverter.hexAsciiToDigit('f'));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIncorerctHexAsciiToDigit() {
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(' '));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('!'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('('));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('+'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('/'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(':'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(';'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('@'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('G'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('Z'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('_'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('`'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('g'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('z'));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('|'));
	}

	@Test
	void testCorrectDigitToHexAscii() {
		try {
			Assertions.assertEquals('0', DataConverter.digitToHexAscii(0x00));
			Assertions.assertEquals('1', DataConverter.digitToHexAscii(0x01));
			Assertions.assertEquals('2', DataConverter.digitToHexAscii(0x02));
			Assertions.assertEquals('3', DataConverter.digitToHexAscii(0x03));
			Assertions.assertEquals('4', DataConverter.digitToHexAscii(0x04));
			Assertions.assertEquals('5', DataConverter.digitToHexAscii(0x05));
			Assertions.assertEquals('6', DataConverter.digitToHexAscii(0x06));
			Assertions.assertEquals('7', DataConverter.digitToHexAscii(0x07));
			Assertions.assertEquals('8', DataConverter.digitToHexAscii(0x08));
			Assertions.assertEquals('9', DataConverter.digitToHexAscii(0x09));
			Assertions.assertEquals('A', DataConverter.digitToHexAscii(0x0A));
			Assertions.assertEquals('B', DataConverter.digitToHexAscii(0x0B));
			Assertions.assertEquals('C', DataConverter.digitToHexAscii(0x0C));
			Assertions.assertEquals('D', DataConverter.digitToHexAscii(0x0D));
			Assertions.assertEquals('E', DataConverter.digitToHexAscii(0x0E));
			Assertions.assertEquals('F', DataConverter.digitToHexAscii(0x0F));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIncorrectDigitToHexAscii() {
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(-100));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(-1));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(16));
		Assertions.assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(100));
	}

	@Test
	void testByteToHexString() {
		try {
			Assertions.assertEquals("00", DataConverter.byteToHexString(0x00));
			Assertions.assertEquals("0A", DataConverter.byteToHexString(0x0A));
			Assertions.assertEquals("CD", DataConverter.byteToHexString(0xCD));
			Assertions.assertEquals("FF", DataConverter.byteToHexString(0xFF));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0x1FF));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0xF00));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0xFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testWordToHexString() {
		try {
			Assertions.assertEquals("0000", DataConverter.wordToHexString(0x0000));
			Assertions.assertEquals("0001", DataConverter.wordToHexString(0x0001));
			Assertions.assertEquals("0A01", DataConverter.wordToHexString(0x0A01));
			Assertions.assertEquals("8000", DataConverter.wordToHexString(0x8000));
			Assertions.assertEquals("EAB1", DataConverter.wordToHexString(0xEAB1));
			Assertions.assertEquals("FFFF", DataConverter.wordToHexString(0xFFFF));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0x1FFFF));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0xF0000));
			Assertions.assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0xFFFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDoubleToHexString() {
		try {
			Assertions.assertEquals("00000000", DataConverter.doubleToHexString(0x00000000));
			Assertions.assertEquals("00000001", DataConverter.doubleToHexString(0x00000001));
			Assertions.assertEquals("12345678", DataConverter.doubleToHexString(0x12345678));
			Assertions.assertEquals("80000000", DataConverter.doubleToHexString(0x80000000));
			Assertions.assertEquals("9ABCDEF0", DataConverter.doubleToHexString(0x9ABCDEF0));
			Assertions.assertEquals("FFFFFFFF", DataConverter.doubleToHexString(0xFFFFFFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}

}
