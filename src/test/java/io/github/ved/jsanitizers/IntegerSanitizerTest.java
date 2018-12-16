package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerSanitizerTest {
	
	@Test
	void simpleNumber(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void highNumber(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void simpleNumberNegative(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithSpace(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithFunkySpaces(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void highNumberNegative(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithSpace(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithFunkySpaces(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValue(value);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void emptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void textOnly(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void simpleNumberMaxed(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void highNumberMaxed(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 1234567);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void simpleNumberNegativeMaxed(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithSpaceMaxed(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithFunkySpacesMaxed(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, 5);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void highNumberNegativeMaxed(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithSpaceMaxed(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithFunkySpacesMaxed(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValueMax(value, -123);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void emptyValueMaxed(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void textOnlyMaxed(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void aboveMaxed(){
		
		String value = "10";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMax(value, 5);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_HIGHER_THAN_MAX,
				exception.getErrorCode());
		
	}
	
	@Test
	void simpleNumberMined(){
		
		String value = "1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, 0);
		
		assertEquals(1, result);
		
	}
	
	@Test
	void highNumberMined(){
		
		String value = "123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, 123);
		
		assertEquals(123456, result);
		
	}
	
	@Test
	void simpleNumberNegativeMined(){
		
		String value = "-1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithSpaceMined(){
		
		String value = "- 1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void simpleNumberNegativeWithFunkySpacesMined(){
		
		String value = "-   \t  1";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -3);
		
		assertEquals(-1, result);
		
	}
	
	@Test
	void highNumberNegativeMined(){
		
		String value = "-123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithSpaceMined(){
		
		String value = "- 123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void highNumberNegativeWithFunkySpacesMined(){
		
		String value = "-   \t  123456";
		
		int result = IntegerSanitizer.sanitizeValueMin(value, -1234567);
		
		assertEquals(-123456, result);
		
	}
	
	@Test
	void emptyValueMined(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_EMPTY, exception.getErrorCode());
		
	}
	
	@Test
	void textOnlyMined(){
		
		String value = "This is not ok";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_NOT_A_NUMBER,
				exception.getErrorCode());
		
	}
	
	@Test
	void underMined(){
		
		String value = "5";
		
		Executable shouldThrowBadFormatException = () -> IntegerSanitizer
				.sanitizeValueMin(value, 10);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(IntegerSanitizer.FORMAT_LOWER_THAN_MIN,
				exception.getErrorCode());
		
	}
	
}
