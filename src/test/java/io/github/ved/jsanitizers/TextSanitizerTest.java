package io.github.ved.jsanitizers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextSanitizerTest {
	
	@Test
	void nullValue(){
		
		String value = null;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("", result);
		
	}
	
	@Test
	void stringValue(){
		
		String value = "hi";
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void integerValue(){
		
		int value = 123;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("123", result);
		
	}
	
	@Test
	void charValue(){
		
		char value = 'a';
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("a", result);
		
	}
	
	@Test
	void doubleValue(){
		
		double value = 2.023;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("2.023", result);
		
	}
	
	@Test
	void floatValue(){
		
		float value = 2.023f;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("2.023", result);
		
	}
	
	@Test
	void byteValue(){
		
		byte value = 12;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("12", result);
		
	}
	
	@Test
	void shortValue(){
		
		short value = 123;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("123", result);
		
	}
	
	@Test
	void objectValue(){
		
		Object value = new Object(){
			@Override
			public String toString(){
				return "String value";
			}
		};
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("String value", result);
		
	}
	
}
