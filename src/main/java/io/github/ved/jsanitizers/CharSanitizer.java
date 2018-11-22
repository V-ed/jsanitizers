package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

/**
 * Utility that sanitizes char objects.
 */
public interface CharSanitizer {
	
	/**
	 * The value's String representation is not of length 1
	 */
	int FORMAT_LENGTH = 1;
	
	/**
	 * Sanitizes any object's String value to convert to a char. Throws a
	 * {@link BadFormatException} if the value given is not a single character
	 * object.
	 * 
	 * @param value
	 *            Any object to extract as a single character value. It uses the
	 *            return of
	 *            {@link TextLengthSanitizer#sanitizeValue(Object, int)} to
	 *            parse the value with a length of only one.
	 * @return A {@code char} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_LENGTH} : if the value's String
	 *             representation is not of length 1.</li>
	 *             </ul>
	 */
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
