package io.github.ved.jsanitizers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
	
	@Test
	void protectRegexCharClean(){
		
		String result;
		
		result = Utils.protectRegexChar('a');
		
		assertEquals("a", result);
		
		result = Utils.protectRegexChar('f');
		
		assertEquals("f", result);
		
		result = Utils.protectRegexChar('z');
		
		assertEquals("z", result);
		
		result = Utils.protectRegexChar('A');
		
		assertEquals("A", result);
		
		result = Utils.protectRegexChar('F');
		
		assertEquals("F", result);
		
		result = Utils.protectRegexChar('Z');
		
		assertEquals("Z", result);
		
	}
	
	@Test
	void protectRegexCharNeedsProtection(){
		
		char[] charsToProtect = new char[]
		{
			'.',
			'\\',
			'(',
			')',
			'{',
			'}',
			'[',
			']',
			'!',
			'?',
			'<',
			'>',
			'=',
			'+',
			'*',
			'&',
			'^',
			'$',
			',',
			'|',
			':',
			'-'
		};
		
		String result;
		
		for(char charToProtect : charsToProtect){
			
			result = Utils.protectRegexChar(charToProtect);
			
			assertEquals("\\" + charToProtect, result);
			
		}
		
	}
	
}