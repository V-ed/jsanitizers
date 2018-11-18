package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CharSanitizerTest {
	
	@Test
	void testSingleCharLetter(){
		
		String value = "a";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('a', result);
		
	}
	
	@Test
	void testSingleCharNumber(){
		
		String value = "1";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('1', result);
		
	}
	
	@Test
	void testSingleCharLetterSpaces(){
		
		String value = "   a   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('a', result);
		
	}
	
	@Test
	void testSingleCharNumberSpaces(){
		
		String value = "   1   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('1', result);
		
	}
	
	@Test
	void testSpecialCharNumber(){
		
		String value = "[";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('[', result);
		
	}
	
	@Test
	void testSpecialCharNumberSpaces(){
		
		String value = "   [   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('[', result);
		
	}
	
	@Test
	void testEmptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> CharSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(CharSanitizer.FORMAT_LENGTH, exception.getErrorCode());
		
	}
	
	@Test
	void testTwoOrMoreChars(){
		
		String value = "ab";
		Executable shouldThrowBadFormatException = () -> CharSanitizer
				.sanitizeValue(value);
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		assertEquals(CharSanitizer.FORMAT_LENGTH, exception.getErrorCode());
		
		String value2 = "abcd";
		shouldThrowBadFormatException = () -> CharSanitizer
				.sanitizeValue(value2);
		exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		assertEquals(CharSanitizer.FORMAT_LENGTH, exception.getErrorCode());
		
	}
	
}
