package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility that sanitizes text objects with the condition of matching a regex.
 */
public class TextRegexSanitizer extends TextSanitizer {
	
	/**
	 * The value does not match the given regex's pattern
	 */
	public static final int FORMAT_NOT_MATCHING_PATTERN = 1;
	
	protected TextRegexSanitizer(){}
	
	/**
	 * Sanitizes any object to a String value and confirm its format using the
	 * given {@code regexToMatch} parameter.
	 *
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param regexToMatch
	 *            The regex to match the given value against.
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_NOT_MATCHING_PATTERN}: if the String does
	 *             not match the pattern given in the {@code regexToMatch}
	 *             parameter.</li>
	 *             </ul>
	 * @throws PatternSyntaxException
	 *             Thrown if the regex given (in the parameter
	 *             {@code regexToMatch}) is not a valid pattern.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, String regexToMatch)
			throws BadFormatException, PatternSyntaxException{
		return TextRegexSanitizer.sanitizeValue(value, regexToMatch, false);
	}
	
	/**
	 * Sanitizes any object to a String value and confirm its format using the
	 * given {@code regexToMatch} parameter. When the parameter
	 * {@code isInverted} is set to {@code true}, the logic is inverted and a
	 * {@link BadFormatException} throws when the value DOES match.
	 *
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param regexToMatch
	 *            The regex to match the given value against.
	 * @param isInverted
	 *            Sets a flag that makes the method throw a
	 *            {@link BadFormatException} when the given value actually
	 *            matches (instead of throwing when not matching) if set to
	 *            {@code true}.
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_NOT_MATCHING_PATTERN}: if the String does
	 *             not match the pattern given in the {@code regexToMatch}
	 *             parameter (or matches if parameter {@code isInverted} is set
	 *             to {@code true}).</li>
	 *             </ul>
	 * @throws PatternSyntaxException
	 *             Thrown if the regex given (in the parameter
	 *             {@code regexToMatch}) is not a valid pattern.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, String regexToMatch,
			boolean isInverted) throws BadFormatException,
			PatternSyntaxException{
		return TextRegexSanitizer.sanitizeValue(value, regexToMatch,
				isInverted, false);
	}
	
	/**
	 * Sanitizes any object to a String value and confirm its format using the
	 * given {@code regexToMatch} parameter. When the parameter
	 * {@code isInverted} is set to {@code true}, the logic is inverted and a
	 * {@link BadFormatException} throws when the value DOES match.
	 *
	 * @param value
	 *            Any object to extract a String value out of it using
	 *            {@link TextSanitizer#sanitizeValue(Object)}.
	 * @param regexToMatch
	 *            The regex to match the given value against.
	 * @param isInverted
	 *            Sets a flag that makes the method throw a
	 *            {@link BadFormatException} when the given value actually
	 *            matches (instead of throwing when not matching) if set to
	 *            {@code true}.
	 * @param isSubFormat
	 *            Sets a flag that makes the method modify the regex to make the
	 *            given regex part of the value given. For example, if the regex
	 *            given is {@code "hi!?"}, all of those Strings (between quotes)
	 *            are valid : {@code "hi mate!"}, {@code "oh, hi!"},
	 *            {@code "you, hi there!"}, while this would be invalid :
	 *            {@code "oh, h_i!"}.
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_NOT_MATCHING_PATTERN}: if the String does
	 *             not match the pattern given in the {@code regexToMatch}
	 *             parameter (or matches if parameter {@code isInverted} is set
	 *             to {@code true}).</li>
	 *             </ul>
	 * @throws PatternSyntaxException
	 *             Thrown if the regex given (in the parameter
	 *             {@code regexToMatch}) is not a valid pattern.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, String regexToMatch,
			boolean isInverted, boolean isSubFormat) throws BadFormatException,
			PatternSyntaxException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(regexToMatch != null){
			
			String realRegex = regexToMatch;
			
			if(isSubFormat){
				realRegex = regexToMatch.replaceAll(
						"^(\\^)?(?!\\.\\*\\(?)(.*)(?!\\)?\\.\\*)(\\$)?$",
						"$1.*($2).*$3");
			}
			
			// Test if Regex provided is valid
			Pattern pattern = Pattern.compile(realRegex);
			
			// Test regex and invert if we need to
			if(pattern.matcher(stringValue).matches() == isInverted){
				throw new BadFormatException(
						"Value does not match the required pattern!",
						FORMAT_NOT_MATCHING_PATTERN);
			}
			
		}
		
		return stringValue;
		
	}
	
}
