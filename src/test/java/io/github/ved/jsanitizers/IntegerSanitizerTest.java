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
	
}
