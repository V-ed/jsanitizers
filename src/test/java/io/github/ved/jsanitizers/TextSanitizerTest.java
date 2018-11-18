package io.github.ved.jsanitizers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextSanitizerTest {
	
	@Test
	void testString(){
		
		String value = "hi";
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("hi", result);
		
	}
	
	@Test
	void testInteger(){
		
		int value = 123;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("123", result);
		
	}
	
	@Test
	void testChar(){
		
		char value = 'a';
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("a", result);
		
	}
	
	@Test
	void testDouble(){
		
		double value = 2.023;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("2.023", result);
		
	}
	
	@Test
	void testFloat(){
		
		float value = 2.023f;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("2.023", result);
		
	}
	
	@Test
	void testByte(){
		
		byte value = 12;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("12", result);
		
	}
	
	@Test
	void testShort(){
		
		short value = 123;
		
		String result = TextSanitizer.sanitizeValue(value);
		
		assertEquals("123", result);
		
	}
	
	@Test
	void testObject(){
		
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
