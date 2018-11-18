package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerSanitizerTest {
	
	@Test
	void testSimpleNumber(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void testHighNumber(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void testSimpleNumberNegative(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithSpace(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithFunkySpaces(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testHighNumberNegative(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithSpace(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithFunkySpaces(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testEmptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void testTextOnly(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void testSimpleNumberMaxed(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void testHighNumberMaxed(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 1234567);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeMaxed(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithSpaceMaxed(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithFunkySpacesMaxed(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testHighNumberNegativeMaxed(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithSpaceMaxed(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithFunkySpacesMaxed(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testEmptyValueMaxed(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void testTextOnlyMaxed(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void testAboveMaxed(){
		
		String value = "10";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 5);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_HIGHER_THAN_MAX,
				exception.getErrorCode());
		
	}
	
	@Test
	void testSimpleNumberMined(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, 0);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void testHighNumberMined(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, 123);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeMined(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithSpaceMined(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testSimpleNumberNegativeWithFunkySpacesMined(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void testHighNumberNegativeMined(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithSpaceMined(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testHighNumberNegativeWithFunkySpacesMined(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void testEmptyValueMined(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void testTextOnlyMined(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void testUnderMined(){
		
		String value = "5";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 10);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_LOWER_THAN_MIN,
				exception.getErrorCode());
		
	}
	
}
