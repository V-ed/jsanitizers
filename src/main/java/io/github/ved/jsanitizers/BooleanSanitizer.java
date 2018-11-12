package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface BooleanSanitizer {
	
	int FORMAT_NOT_BOOLEAN = 1;
	
	static boolean sanitizeValue(Object value) throws BadFormatException{
		
		boolean castedValue;
		
		try{
			
			if(value instanceof String){
				
				String stringValue = (String)value;
				
				if(!stringValue.matches("^(?i)true|false$")){
					throw new Exception();
				}
				
				castedValue = Boolean.valueOf(stringValue);
				
			}
			else{
				
				castedValue = (boolean)value;
				
			}
			
		}
		catch(Exception e){
			throw new BadFormatException(
					"Value cannot be something else than \"true\" or \"false\"!",
					FORMAT_NOT_BOOLEAN);
		}
		
		return castedValue;
		
	}
	
}
