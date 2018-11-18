package io.github.ved.jsanitizers.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadFormatExceptionTest {
	
	@Test
	void testConstructorSimple(){
		
		BadFormatException e = new BadFormatException();
		
		assertEquals(-1, e.getErrorCode());
		
	}
	
	@Test
	void testConstructorWithCode(){
		
		BadFormatException e = new BadFormatException(1);
		
		assertEquals(1, e.getErrorCode());
		
	}
	
	@Test
	void testConstructorWithMessage(){
		
		BadFormatException e = new BadFormatException(
				"This is an error message");
		
		assertEquals("This is an error message", e.getMessage());
		assertEquals(-1, e.getErrorCode());
		
	}
	
	@Test
	void testConstructorWithMessageAndCode(){
		
		BadFormatException e = new BadFormatException(
				"This is an error message", 1);
		
		assertEquals("This is an error message", e.getMessage());
		assertEquals(1, e.getErrorCode());
		
	}
	
}
