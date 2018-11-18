package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface BooleanSanitizer {
	
	int FORMAT_NOT_BOOLEAN = 1;
	
	static boolean sanitizeValue(Object value) throws BadFormatException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(!stringValue.matches("^(?i)true|false$")){
			throw new BadFormatException(
					"Value cannot be something else than \"true\" or \"false\"!",
					FORMAT_NOT_BOOLEAN);
		}
		
		return Boolean.valueOf(stringValue);
		
	}
	
}
