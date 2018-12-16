package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

/**
 * Utility that sanitizes text objects with a certain length condition.
 */
public class TextLengthSanitizer extends TextSanitizer {
	
	/**
	 * The value's String length is lower than the minimum required
	 */
	public static final int FORMAT_MIN_LENGTH = 1;
	
	/**
	 * The value's String length is higher than the maximum required
	 */
	public static final int FORMAT_MAX_LENGTH = 2;
	
	protected TextLengthSanitizer(){}
	
	/**
	 * Sanitizes any object to a String value with a length that is at least the
	 * given {@code minLength} parameter.
	 * 
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param minLength
	 *            The minimum length accepted for the String
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_MIN_LENGTH} : if the String's length is
	 *             lower than the given {@code minLength} parameter.</li>
	 *             </ul>
	 * @see TextSanitizer
	 */
	public static String sanitizeValueMin(Object value, int minLength)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, minLength,
				Integer.MAX_VALUE);
	}
	
	/**
	 * Sanitizes any object to a String value with a length that is at most the
	 * given {@code maxLength} parameter.
	 * 
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param maxLength
	 *            The maximum length accepted for the String
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_MAX_LENGTH} : if the String's length is
	 *             higher than the given {@code maxLength} parameter.</li>
	 *             </ul>
	 * @see TextSanitizer
	 */
	public static String sanitizeValueMax(Object value, int maxLength)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				maxLength);
	}
	
	/**
	 * Sanitizes any object to a String value with a length that is only the
	 * given {@code maxLength} parameter.
	 * 
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param length
	 *            The length accepted for the String
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_MIN_LENGTH} : if the String's length is
	 *             lower than the given {@code length} parameter.</li>
	 *             <li>{@link #FORMAT_MAX_LENGTH} : if the String's length is
	 *             higher than the given {@code length} parameter.</li>
	 *             </ul>
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, int length)
			throws BadFormatException{
		return TextLengthSanitizer.sanitizeValue(value, length, length);
	}
	
	/**
	 * Sanitizes any object to a String value with a length that is at least the
	 * given {@code minLength} parameter and at most the given {@code maxLength}
	 * parameter.
	 * 
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param minLength
	 *            The minimum length accepted for the String
	 * @param maxLength
	 *            The maximum length accepted for the String
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_MIN_LENGTH} : if the String's length is
	 *             lower than the given {@code minLength} parameter.</li>
	 *             <li>{@link #FORMAT_MAX_LENGTH} : if the String's length is
	 *             higher than the given {@code maxLength} parameter.</li>
	 *             </ul>
	 * @throws IllegalArgumentException
	 *             Thrown if the {@code minLength} parameter's integer value is
	 *             bigger than the {@code maxLength} parameter's integer value.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, int minLength,
			int maxLength) throws BadFormatException, IllegalArgumentException{
		
		if(minLength > maxLength){
			throw new IllegalArgumentException(
					"The argument minLength shouldn't be bigger than the argument maxLength.");
		}
		
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
