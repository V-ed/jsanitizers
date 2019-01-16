package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

/**
 * Utility that sanitizes integer objects.
 */
public class IntegerSanitizer extends Sanitizer<Integer> {
	
	/**
	 * The value's String representation is empty (either null or of length 0)
	 */
	public static final int FORMAT_EMPTY = TextNotEmptySanitizer.FORMAT_EMPTY;
	
	/**
	 * The value is not a number
	 */
	public static final int FORMAT_NOT_A_NUMBER = 2;
	
	/**
	 * The value's number is lesser than the minimum required
	 */
	public static final int FORMAT_LOWER_THAN_MIN = 3;
	
	/**
	 * The value's number is higher than the maximum required
	 */
	public static final int FORMAT_HIGHER_THAN_MAX = 4;
	
	protected IntegerSanitizer(){}
	
	/**
	 * Sanitizes any object's String value to convert to an int. Throws a
	 * {@link BadFormatException} if the value given is not a number.
	 * 
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @return An {@code int} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the value's String
	 *             representation is empty : either null or of length 0.</li>
	 *             <li>{@link #FORMAT_NOT_A_NUMBER} : if the value given is not
	 *             a number.</li>
	 *             </ul>
	 */
	public static int sanitizeValue(Object value) throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
	}
	
	/**
	 * Sanitizes any object's String value to convert to an int. This method
	 * also allow to setup a minimum for the value to be at - any value under
	 * the given {@code minValue} will throw a BadFormatException. Throws a
	 * {@link BadFormatException} if the value given is not a number.
	 * 
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @param minValue
	 *            The minimum integer value that the value can be.
	 * @return An {@code int} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the value's String
	 *             representation is empty : either null or of length 0.</li>
	 *             <li>{@link #FORMAT_NOT_A_NUMBER} : if the value given is not
	 *             a number.</li>
	 *             <li>{@link #FORMAT_LOWER_THAN_MIN} : if the value given is a
	 *             number lower than the given {@code minValue} parameter.</li>
	 *             </ul>
	 */
	public static int sanitizeValueMin(Object value, int minValue)
			throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, minValue,
				Integer.MAX_VALUE);
	}
	
	/**
	 * Sanitizes any object's String value to convert to an int. This method
	 * also allow to setup a maximum for the value to be at - any value above
	 * the given {@code maxValue} will throw a BadFormatException. Throws a
	 * {@link BadFormatException} if the value given is not a number.
	 * 
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @param maxValue
	 *            The maximum integer value that the value can be.
	 * @return An {@code int} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the value's String
	 *             representation is empty : either null or of length 0.</li>
	 *             <li>{@link #FORMAT_NOT_A_NUMBER} : if the value given is not
	 *             a number.</li>
	 *             <li>{@link #FORMAT_HIGHER_THAN_MAX} : if the value given is a
	 *             number higher than the given {@code maxValue} parameter.</li>
	 *             </ul>
	 */
	public static int sanitizeValueMax(Object value, int maxValue)
			throws BadFormatException{
		return IntegerSanitizer.sanitizeValue(value, Integer.MIN_VALUE,
				maxValue);
	}
	
	/**
	 * Sanitizes any object's String value to convert to an int. This method
	 * also allow to setup a minimum and maximum for the value to be at - any
	 * value lower the given {@code minValue} or above the given
	 * {@code maxValue} will throw a BadFormatException. Throws a
	 * {@link BadFormatException} if the value given is not a number.
	 *
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @param minValue
	 *            The minimum integer value that the value can be.
	 * @param maxValue
	 *            The maximum integer value that the value can be.
	 * @return An {@code int} representative of the parameter's given value.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_EMPTY} : if the value's String
	 *             representation is empty : either null or of length 0.</li>
	 *             <li>{@link #FORMAT_NOT_A_NUMBER} : if the value given is not
	 *             a number.</li>
	 *             <li>{@link #FORMAT_LOWER_THAN_MIN} : if the value given is a
	 *             number lower than the given {@code minValue} parameter.</li>
	 *             <li>{@link #FORMAT_HIGHER_THAN_MAX} : if the value given is a
	 *             number higher than the given {@code maxValue} parameter.</li>
	 *             </ul>
	 */
	public static int sanitizeValue(Object value, int minValue, int maxValue)
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
