package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class TextRegexSanitizerTest {
	
	@Test
	void simpleRegex(){
		
		String value = "hi";
		
		String regex = "hi";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void quantifiers(){
		
		String value = "hi";
		String regex = "hi!?";
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		assertEquals("hi", result);
		
		value = "hi!";
		result = TextRegexSanitizer.sanitizeValue(value, regex);
		assertEquals("hi!", result);
		
	}
	
	@Test
	void protectedQuantifiers(){
		
		String value = "hi?";
		
		String regex = "hi\\?";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex);
		
		assertEquals("hi?", result);
		
	}
	
	@Test
	void badRegexPattern(){
		
		String value = "hi";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void formatNotMatching(){
		
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
	void invertedSimpleRegex(){
		
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
	void invertedQuantifiers(){
		
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
	void invertedProtectedQuantifiers(){
		
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
	void invertedBadRegexPattern(){
		
		String value = "hi";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, true);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void invertedFormatNotMatching(){
		
		String value = "hi";
		
		String regex = "hi!";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, true);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void isSubFormatSimpleRegex(){
		
		String value = "hi   this is other text";
		
		String regex = "hi";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				true);
		
		assertEquals("hi   this is other text", result);
		
	}
	
	@Test
	void isSubFormatQuantifiers(){
		
		String value = "hi  okay!";
		String regex = "hi!?";
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				true);
		assertEquals("hi  okay!", result);
		
		value = "hi! this works!";
		result = TextRegexSanitizer.sanitizeValue(value, regex, false, true);
		assertEquals("hi! this works!", result);
		
	}
	
	@Test
	void isSubFormatProtectedQuantifiers(){
		
		String value = "hi? Okay!";
		
		String regex = "hi\\?";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				true);
		
		assertEquals("hi? Okay!", result);
		
	}
	
	@Test
	void isSubFormatAlreadySubFormat(){
		
		String value = "hi? Okay!";
		
		String regex = ".*hi.*";
		
		String result = TextRegexSanitizer.sanitizeValue(value, regex, false,
				true);
		
		assertEquals("hi? Okay!", result);
		
	}
	
	@Test
	void isSubFormatBadRegexPattern(){
		
		String value = "hi this might not work...";
		
		String regex = "hi[";
		
		Executable shouldThrowPatternSyntaxException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, false, true);
		
		assertThrows(PatternSyntaxException.class,
				shouldThrowPatternSyntaxException);
		
	}
	
	@Test
	void isSubFormatFormatNotMatching(){
		
		String value = "hi this might not work...";
		
		String regex = "hi!";
		
		Executable shouldThrowBadFormatException = () -> TextRegexSanitizer
				.sanitizeValue(value, regex, false, true);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN,
				exception.getErrorCode());
		
	}
	
}
