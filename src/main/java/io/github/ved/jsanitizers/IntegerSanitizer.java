package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface IntegerSanitizer {
	
	int FORMAT_EMPTY = TextNotEmptySanitizer.FORMAT_EMPTY;
	int FORMAT_NOT_A_NUMBER = 2;
	int FORMAT_LOWER_THAN_MIN = 3;
	int FORMAT_HIGHER_THAN_MAX = 4;
	
	static int sanitizeValue(Object value) throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
	}
	
	static int sanitizeValueMin(Object value, int minValue)
			throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, minValue,
				Integer.MAX_VALUE);
	}
	
	static int sanitizeValueMax(Object value, int maxValue)
			throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				maxValue);
	}
	
	static int sanitizeValue(Object value, int minValue, int maxValue)
			throws BadFormatException{
		
		int castedValue;
		
		try{
			
			String stringValue = TextNotEmptySanitizer.sanitizeValue(value);
			
			stringValue = stringValue.replaceAll("^(-)\\s+(\\d+)$", "$1$2");
			
			castedValue = Integer.valueOf(stringValue);
			
		}
		catch(NumberFormatException e){
			throw new BadFormatException("Value is not a number!",
					FORMAT_NOT_A_NUMBER);
		}
		
		if(minValue != Integer.MIN_VALUE && castedValue < minValue){
			throw new BadFormatException("Value (" + castedValue
					+ ") is lower than the minimum required (" + minValue
					+ ")!", FORMAT_LOWER_THAN_MIN);
		}
		else if(maxValue != Integer.MAX_VALUE && castedValue > maxValue){
			throw new BadFormatException("Value (" + castedValue
					+ ") is higher than the maximum permitted (" + maxValue
					+ ")!", FORMAT_HIGHER_THAN_MAX);
		}
		
		return castedValue;
		
	}
	
}
