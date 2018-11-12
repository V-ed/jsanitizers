package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface TextNotEmptySanitizer {
	
	int FORMAT_EMPTY = 1;
	
	static String sanitizeValue(Object value) throws BadFormatException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(stringValue.length() == 0){
			throw new BadFormatException("Value cannot be empty!", FORMAT_EMPTY);
		}
		
		return stringValue;
		
	}
	
}
