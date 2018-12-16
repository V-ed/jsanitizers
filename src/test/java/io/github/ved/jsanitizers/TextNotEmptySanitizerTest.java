package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class TextNotEmptySanitizerTest {
	
	@Test
	void string(){
		
		String value = "hi";
		
		String result = TextNotEmptySanitizer.sanitizeValue(value);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void stringLeadingSpaces(){
		
		String value = "     hi";
		
		String result = TextNotEmptySanitizer.sanitizeValue(value);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void emptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> TextNotEmptySanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextNotEmptySanitizer.FORMAT_EMPTY,
				exception.getErrorCode());
		
	}
	
	@Test
	void onlySpaces(){
		
		String value = "         ";
		
		Executable shouldThrowBadFormatException = () -> TextNotEmptySanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextNotEmptySanitizer.FORMAT_EMPTY,
				exception.getErrorCode());
		
	}
	
	@Test
	void onlyFunkySpaces(){
		
		String value = " \t \t  \t   ";
		
		Executable shouldThrowBadFormatException = () -> TextNotEmptySanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextNotEmptySanitizer.FORMAT_EMPTY,
				exception.getErrorCode());
		
	}
	
}
