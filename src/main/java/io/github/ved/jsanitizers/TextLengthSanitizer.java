package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface TextLengthSanitizer {
	
	int FORMAT_MIN_LENGTH = 1;
	int FORMAT_MAX_LENGTH = 2;
	
	static String sanitizeValueMin(Object value, int minLength)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, minLength,
				Integer.MAX_VALUE);
	}
	
	static String sanitizeValueMax(Object value, int maxLength)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				maxLength);
	}
	
	static String sanitizeValue(Object value, int length)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, length, length);
	}
	
	static String sanitizeValue(Object value, int minLength, int maxLength)
			throws BadFormatException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		int stringLength = stringValue.length();
		
		if(minLength != Integer.MIN_VALUE && stringLength < minLength){
			throw new BadFormatException("The value needs to have at least "
					+ minLength + " characters!", FORMAT_MIN_LENGTH);
		}
		else if(maxLength != Integer.MAX_VALUE && stringLength > maxLength){
			throw new BadFormatException("The value cannot have more than "
					+ maxLength + " characters!", FORMAT_MAX_LENGTH);
		}
		
		return stringValue;
		
	}
	
}
