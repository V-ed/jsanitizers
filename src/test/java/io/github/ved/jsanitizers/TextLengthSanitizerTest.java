package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class TextLengthSanitizerTest {
	
	@Test
	void stringWithinBounds(){
		
		String value = "hi";
		
		String result = TextLengthSanitizer.sanitizeValue(value, 1, 10);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void stringMinBoundOnly(){
		
		String value = "hi";
		
		String result = TextLengthSanitizer.sanitizeValueMin(value, 1);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void stringMaxBoundOnly(){
		
		String value = "hi";
		
		String result = TextLengthSanitizer.sanitizeValueMax(value, 10);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void stringSingleLength(){
		
		String value = "hi";
		
		String result = TextLengthSanitizer.sanitizeValue(value, 2);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void stringSingleLengthAbove(){
		
		String value = "hi";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValue(value, 1);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MAX_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringSingleLengthUnder(){
		
		String value = "hi";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValue(value, 3);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MIN_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringBelowBounds(){
		
		String value = "hi";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValue(value, 4, 10);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MIN_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringAboveBounds(){
		
		String value = "hi there";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValue(value, 2, 4);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MAX_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringBelowBoundsMinBoundOnly(){
		
		String value = "hi";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValueMin(value, 4);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MIN_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringAboveBoundsMaxBoundOnly(){
		
		String value = "hi there";
		
		Executable shouldThrowBadFormatException = () -> TextLengthSanitizer
				.sanitizeValueMax(value, 4);
		
		BadFormatException exception = assertThrows(BadFormatException.class,
				shouldThrowBadFormatException);
		
		assertEquals(TextLengthSanitizer.FORMAT_MAX_LENGTH,
				exception.getErrorCode());
		
	}
	
	@Test
	void stringMinBoundLowerThanMaxBound(){
		
		String value = "hi here";
		
		Executable shouldThrowIllegalArgumentException = () -> TextLengthSanitizer
				.sanitizeValue(value, 4, 2);
		
		assertThrows(IllegalArgumentException.class,
				shouldThrowIllegalArgumentException);
		
	}
	
}
