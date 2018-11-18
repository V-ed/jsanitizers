package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class TextRegexSanitizerTest {
	
	@Test
	void testSimpleRegex(){
		
		String value = "hi";
		
		String regex = "hi";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void testQuantifiers(){
		
		String value = "hi";
		String regex = "hi!?";
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		assertEquals("hi", result);
		
		value = "hi!";
		result = TextRegexSanitizer.sanitizeValue(value, regex);
		assertEquals("hi!", result);
		
	}
	
	@Test
	void testProtectedQuantifiers(){
		
		String value = "hi?";
		
		String regex = "hi\\?";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		
		assertEquals("hi?", result);
		
	}
	
	@Test
	void testBadRegexPattern(){
		
		String value = "hi";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void testFormatNotMatching(){
		
		String value = "hi";
		
		String regex = "hi!";
		
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testInvertedSimpleRegex(){
		
		String value = "hi";
		
		String regex = "hi";
		
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, true);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testInvertedQuantifiers(){
		
		String value = "hi";
		String regex = "hi!?";
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, true);
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
		String value2 = "hi!";
		shouldThrowBadFormatException = () -> TextRegexSanitizer.sanitizeValue(
				value2, regex, true);
		exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testInvertedProtectedQuantifiers(){
		
		String value = "hi?";
		
		String regex = "hi\\?";
		
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, true);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
	@Test
	void testInvertedBadRegexPattern(){
		
		String value = "hi";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, true);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void testInvertedFormatNotMatching(){
		
		String value = "hi";
		
		String regex = "hi!";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, true);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void testShouldNotBoxSimpleRegex(){
		
		String value = "hi   this is other text";
		
		String regex = "hi";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				false);
		
		assertEquals("hi   this is other text", result);
		
	}
	
	@Test
	void testShouldNotBoxQuantifiers(){
		
		String value = "hi  okay!";
		String regex = "hi!?";
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				false);
		assertEquals("hi  okay!", result);
		
		value = "hi! this works!";
		result = TextRegexSanitizer.sanitizeValue(value, regex, false, false);
		assertEquals("hi! this works!", result);
		
	}
	
	@Test
	void testShouldNotBoxProtectedQuantifiers(){
		
		String value = "hi? Okay!";
		
		String regex = "hi\\?";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				false);
		
		assertEquals("hi? Okay!", result);
		
	}
	
	@Test
	void testShouldNotBoxBadRegexPattern(){
		
		String value = "hi this might not work...";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, false, false);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void testShouldNotBoxFormatNotMatching(){
		
		String value = "hi this might not work...";
		
		String regex = "hi!";
		
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, false, false);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
}
