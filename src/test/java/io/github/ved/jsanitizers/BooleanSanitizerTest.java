package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanSanitizerTest {
	
	@Test
	void trueUnderscore(){
		
		String value = "true";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void trueUnderscoreWithSpaces(){
		
		String value = "   true   ";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void trueUppercase(){
		
		String value = "TRUE";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertTrue(result);
		
	}
	
	@Test
	void trueFunkyCase(){
		
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
	void falseUnderscore(){
		
		String value = "false";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertFalse(result);
		
	}
	
	@Test
	void falseUppercase(){
		
		String value = "FALSE";
		
		boolean result = BooleanSanitizer.sanitizeValue(value);
		
		assertFalse(result);
		
	}
	
	@Test
	void falseFunkyCase(){
		
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
	void nonBoolean(){
		
		String value = "other";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void startsWithTrue(){
		
		String value = "true stuff";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void endsWithFalse(){
		
		String value = "stuff is false";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void startsWithFalse(){
		
		String value = "false stuff";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
	@Test
	void endsWithTrue(){
		
		String value = "stuff is true";
		
		Executable shouldThrowBadFormatException = () -> BooleanSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(BooleanSanitizer.FORMAT_NOT_BOOLEAN,
				exception.getErrorCode());
		
	}
	
}
