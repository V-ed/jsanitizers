package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

/**
 * Utility that sanitizes boolean objects.
 */
public interface BooleanSanitizer {
	
	/**
	 * The value is empty
	 */
	int FORMAT_EMPTY = TextNotEmptySanitizer.FORMAT_EMPTY;
	
	/**
	 * The value is not a boolean (after being parsed)
	 */
	int FORMAT_NOT_BOOLEAN = 2;
	
	/**
	 * Sanitizes any object's String value to convert to a boolean. Throws a
	 * {@link BadFormatException} if the value given is not a boolean.
	 * 
	 * @param value
	 *            Any object to test for it's true or false return value. It
	 *            uses the return of
	 *            {@link TextNotEmptySanitizer#sanitizeValue(Object)} to parse
	 *            if the text matches the text "true" or "false", ignoring
	 *            cases.
	 * @return A {@code boolean} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the value is empty</li>
	 *             <li>{@link #FORMAT_NOT_BOOLEAN} : if the value is not a
	 *             boolean (after being parsed)</li>
	 *             </ul>
	 */
	static boolean sanitizeValue(Object value) throws BadFormatException{
		
		String stringValue = TextNotEmptySanitizer.sanitizeValue(value);
		
		if(!stringValue.matches("(?i)^(true|false)$")){
			throw new BadFormatException(
					"Value cannot be something else than \"true\" or \"false\"!",
					FORMAT_NOT_BOOLEAN);
		}
		
		return Boolean.valueOf(stringValue);
		
	}
	
}
