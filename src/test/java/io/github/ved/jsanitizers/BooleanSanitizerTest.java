package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanSanitizerTest {
	
	@Test
	void testTrueUnderscore(){
		
		String value = "true";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void testTrueUnderscoreWithSpaces(){
		
		String value = "   true   ";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void testTrueUppercase(){
		
		String value = "TRUE";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void testTrueFunkyCase(){
		
		String value = "tRuE";
		boolean result = BooleanSanitizer.sanitizeValue(value);
		assertTrue(result);
		
		value = "TRue";
		result = BooleanSanitizer.sanitizeValue(value);
		assertTrue(result);
		
		value = "truE";
		result = BooleanSanitizer.sanitizeValue(value);
		assertTrue(result);
		
	}
	
	@Test
	void testFalseUnderscore(){
		
		String value = "false";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertFalse(result);
		
	}
	
	@Test
	void testFalseUppercase(){
		
		String value = "FALSE";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertFalse(result);
		
	}
	
	@Test
	void testFalseFunkyCase(){
		
		String value = "FalsE";
		boolean result = BooleanSanitizer.sanitizeValue(value);
		assertFalse(result);
		
		value = "FAlsE";
		result = BooleanSanitizer.sanitizeValue(value);
		assertFalse(result);
		
		value = "faLse";
		result = BooleanSanitizer.sanitizeValue(value);
		assertFalse(result);
		
	}
	
	@Test
	void testNonBoolean(){
		
		String value = "other";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testStartsWithTrue(){
		
		String value = "true stuff";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testEndsWithFalse(){
		
		String value = "stuff is false";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testStartsWithFalse(){
		
		String value = "false stuff";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testEndsWithTrue(){
		
		String value = "stuff is true";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
}
