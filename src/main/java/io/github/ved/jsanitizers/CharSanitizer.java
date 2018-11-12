package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface CharSanitizer {
	
	int FORMAT_LENGTH = 1;
	
	static char sanitizeValue(Object value) throws BadFormatException{
		
		try{
			
			String stringValue = TextLengthSanitizer.sanitizeValue(value, 1);
			
			return stringValue.charAt(0);
			
		}
		catch(BadFormatException e){
			throw new BadFormatException("Only one character is expected!",
					FORMAT_LENGTH);
		}
		
	}
	
}
