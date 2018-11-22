package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

public interface TextNotEmptySanitizer {
	
	/**
	 * if the value is null or is of length 0
	 */
	int FORMAT_EMPTY = 1;
	
	/**
	 * Sanitizes any object to a String value that cannot be empty (of length
	 * {@code 0}).
	 *
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @return the String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the String's length is 0.</li>
	 *             </ul>
	 * @see TextSanitizer
	 */
	static String sanitizeValue(Object value) throws BadFormatException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(stringValue.length() == 0){
			throw new BadFormatException("Value cannot be empty!", FORMAT_EMPTY);
		}
		
		return stringValue;
		
	}
	
}
