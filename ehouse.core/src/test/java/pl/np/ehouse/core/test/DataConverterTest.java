package pl.np.ehouse.core.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
			assertEquals(0x00, DataConverter.hexAsciiToDigit('0'));
			assertEquals(0x01, DataConverter.hexAsciiToDigit('1'));
			assertEquals(0x02, DataConverter.hexAsciiToDigit('2'));
			assertEquals(0x03, DataConverter.hexAsciiToDigit('3'));
			assertEquals(0x04, DataConverter.hexAsciiToDigit('4'));
			assertEquals(0x05, DataConverter.hexAsciiToDigit('5'));
			assertEquals(0x06, DataConverter.hexAsciiToDigit('6'));
			assertEquals(0x07, DataConverter.hexAsciiToDigit('7'));
			assertEquals(0x08, DataConverter.hexAsciiToDigit('8'));
			assertEquals(0x09, DataConverter.hexAsciiToDigit('9'));
			assertEquals(0x0A, DataConverter.hexAsciiToDigit('A'));
			assertEquals(0x0B, DataConverter.hexAsciiToDigit('B'));
			assertEquals(0x0C, DataConverter.hexAsciiToDigit('C'));
			assertEquals(0x0D, DataConverter.hexAsciiToDigit('D'));
			assertEquals(0x0E, DataConverter.hexAsciiToDigit('E'));
			assertEquals(0x0F, DataConverter.hexAsciiToDigit('F'));
			assertEquals(0x0A, DataConverter.hexAsciiToDigit('a'));
			assertEquals(0x0B, DataConverter.hexAsciiToDigit('b'));
			assertEquals(0x0C, DataConverter.hexAsciiToDigit('c'));
			assertEquals(0x0D, DataConverter.hexAsciiToDigit('d'));
			assertEquals(0x0E, DataConverter.hexAsciiToDigit('e'));
			assertEquals(0x0F, DataConverter.hexAsciiToDigit('f'));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIncorerctHexAsciiToDigit() {
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(' '));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('!'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('('));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('+'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('/'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(':'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit(';'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('@'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('G'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('Z'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('_'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('`'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('g'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('z'));
		assertThrows(DataConvertException.class, () -> DataConverter.hexAsciiToDigit('|'));
	}

	@Test
	void testCorrectDigitToHexAscii() {
		try {
			assertEquals('0', DataConverter.digitToHexAscii(0x00));
			assertEquals('1', DataConverter.digitToHexAscii(0x01));
			assertEquals('2', DataConverter.digitToHexAscii(0x02));
			assertEquals('3', DataConverter.digitToHexAscii(0x03));
			assertEquals('4', DataConverter.digitToHexAscii(0x04));
			assertEquals('5', DataConverter.digitToHexAscii(0x05));
			assertEquals('6', DataConverter.digitToHexAscii(0x06));
			assertEquals('7', DataConverter.digitToHexAscii(0x07));
			assertEquals('8', DataConverter.digitToHexAscii(0x08));
			assertEquals('9', DataConverter.digitToHexAscii(0x09));
			assertEquals('A', DataConverter.digitToHexAscii(0x0A));
			assertEquals('B', DataConverter.digitToHexAscii(0x0B));
			assertEquals('C', DataConverter.digitToHexAscii(0x0C));
			assertEquals('D', DataConverter.digitToHexAscii(0x0D));
			assertEquals('E', DataConverter.digitToHexAscii(0x0E));
			assertEquals('F', DataConverter.digitToHexAscii(0x0F));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIncorrectDigitToHexAscii() {
		assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(-100));
		assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(-1));
		assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(16));
		assertThrows(DataConvertException.class, () -> DataConverter.digitToHexAscii(100));
	}

	@Test
	void testByteToHexString() {
		try {
			assertEquals("00", DataConverter.byteToHexString(0x00));
			assertEquals("0A", DataConverter.byteToHexString(0x0A));
			assertEquals("CD", DataConverter.byteToHexString(0xCD));
			assertEquals("FF", DataConverter.byteToHexString(0xFF));
			assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0x1FF));
			assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0xF00));
			assertThrows(DataConvertException.class, () -> DataConverter.byteToHexString(0xFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testWordToHexString() {
		try {
			assertEquals("0000", DataConverter.wordToHexString(0x0000));
			assertEquals("0001", DataConverter.wordToHexString(0x0001));
			assertEquals("0A01", DataConverter.wordToHexString(0x0A01));
			assertEquals("8000", DataConverter.wordToHexString(0x8000));
			assertEquals("EAB1", DataConverter.wordToHexString(0xEAB1));
			assertEquals("FFFF", DataConverter.wordToHexString(0xFFFF));
			assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0x1FFFF));
			assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0xF0000));
			assertThrows(DataConvertException.class, () -> DataConverter.wordToHexString(0xFFFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testDoubleToHexString() {
		try {
			assertEquals("00000000", DataConverter.doubleToHexString(0x00000000));
			assertEquals("00000001", DataConverter.doubleToHexString(0x00000001));
			assertEquals("12345678", DataConverter.doubleToHexString(0x12345678));
			assertEquals("80000000", DataConverter.doubleToHexString(0x80000000));
			assertEquals("9ABCDEF0", DataConverter.doubleToHexString(0x9ABCDEF0));
			assertEquals("FFFFFFFF", DataConverter.doubleToHexString(0xFFFFFFFF));
		} catch (DataConvertException e) {
			e.printStackTrace();
		}
	}

}
