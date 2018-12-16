package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CharSanitizerTest {
	
	@Test
	void singleCharLetter(){
		
		String value = "a";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('a', result);
		
	}
	
	@Test
	void singleCharNumber(){
		
		String value = "1";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('1', result);
		
	}
	
	@Test
	void singleCharLetterSpaces(){
		
		String value = "   a   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('a', result);
		
	}
	
	@Test
	void singleCharNumberSpaces(){
		
		String value = "   1   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('1', result);
		
	}
	
	@Test
	void specialCharNumber(){
		
		String value = "[";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('[', result);
		
	}
	
	@Test
	void specialCharNumberSpaces(){
		
		String value = "   [   ";
		
		char result = CharSanitizer.sanitizeValue(value);
		
		assertEquals('[', result);
		
	}
	
	@Test
	void emptyValue(){
		
		String value = "";
		
		Executable shouldThrowBadFormatException = () -> CharSanitizer
				.sanitizeValue(value);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(CharSanitizer.FORMAT_LENGTH, exception.getErrorCode());
		
	}
	
	@Test
	void twoOrMoreChars(){
		
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
