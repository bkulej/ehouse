package pl.np.ehouse.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

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
	void testCorrectDigitFromHexAscii() throws DataConvertException {
		assertEquals(0x00, DataConverter.getDigitFromHexAscii('0'));
		assertEquals(0x01, DataConverter.getDigitFromHexAscii('1'));
		assertEquals(0x02, DataConverter.getDigitFromHexAscii('2'));
		assertEquals(0x03, DataConverter.getDigitFromHexAscii('3'));
		assertEquals(0x04, DataConverter.getDigitFromHexAscii('4'));
		assertEquals(0x05, DataConverter.getDigitFromHexAscii('5'));
		assertEquals(0x06, DataConverter.getDigitFromHexAscii('6'));
		assertEquals(0x07, DataConverter.getDigitFromHexAscii('7'));
		assertEquals(0x08, DataConverter.getDigitFromHexAscii('8'));
		assertEquals(0x09, DataConverter.getDigitFromHexAscii('9'));
		assertEquals(0x0A, DataConverter.getDigitFromHexAscii('A'));
		assertEquals(0x0B, DataConverter.getDigitFromHexAscii('B'));
		assertEquals(0x0C, DataConverter.getDigitFromHexAscii('C'));
		assertEquals(0x0D, DataConverter.getDigitFromHexAscii('D'));
		assertEquals(0x0E, DataConverter.getDigitFromHexAscii('E'));
		assertEquals(0x0F, DataConverter.getDigitFromHexAscii('F'));
		assertEquals(0x0A, DataConverter.getDigitFromHexAscii('a'));
		assertEquals(0x0B, DataConverter.getDigitFromHexAscii('b'));
		assertEquals(0x0C, DataConverter.getDigitFromHexAscii('c'));
		assertEquals(0x0D, DataConverter.getDigitFromHexAscii('d'));
		assertEquals(0x0E, DataConverter.getDigitFromHexAscii('e'));
		assertEquals(0x0F, DataConverter.getDigitFromHexAscii('f'));
	}

	@Test
	void testIncorerctDigitFromHexAscii() throws DataConvertException {
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii(' '));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('!'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('('));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('+'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('/'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii(':'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii(';'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('@'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('G'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('Z'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('_'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('`'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('g'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('z'));
		assertThrows(DataConvertException.class, () -> DataConverter.getDigitFromHexAscii('|'));
	}

	@Test
	void testByte() throws DataConvertException {
		List<Integer> data = "0ACD120B".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
		assertEquals(0x0A, DataConverter.getByte(data, 0));
		assertEquals(0xCD, DataConverter.getByte(data, 2));
		assertEquals(0x12, DataConverter.getByte(data, 4));
		assertEquals(0x0B, DataConverter.getByte(data, 6));
		assertThrows(DataConvertException.class, () -> DataConverter.getByte(data, 7));
		assertThrows(DataConvertException.class, () -> DataConverter.getByte(data, 8));
	}

	@Test
	void testWord() throws DataConvertException {
		List<Integer> data = "0ACD120B".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
		assertEquals(0x0ACD, DataConverter.getWord(data, 0));
		assertEquals(0x120B, DataConverter.getWord(data, 4));
		assertThrows(DataConvertException.class, () -> DataConverter.getWord(data, 5));
		assertThrows(DataConvertException.class, () -> DataConverter.getWord(data, 8));
	}

	@Test
	void testDouble() throws DataConvertException {
		List<Integer> data = "0ACD120B10B3113F".chars().mapToObj(value -> (Integer) value).collect(Collectors.toList());
		assertEquals(0x0ACD120BL, DataConverter.getDouble(data, 0));
		assertEquals(0x10B3113FL, DataConverter.getDouble(data, 8));
		assertThrows(DataConvertException.class, () -> DataConverter.getDouble(data, 9));
		assertThrows(DataConvertException.class, () -> DataConverter.getDouble(data, 16));
	}

}
