package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Utility that sanitizes enum objects. By enum objects, it is intended that it
 * starts from a string, gets extracted, then verifies if the objects is
 * contained in the extract list.
 */
public class EnumSanitizer extends Sanitizer<Enum> {
	
	/**
	 * String value is not accepted as an enum definition
	 */
	public static final int FORMAT_NOT_ACCEPTED = TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN;
	
	/**
	 * Value is not present in the list given
	 */
	public static final int FORMAT_NOT_A_CHOICE = 2;
	
	protected EnumSanitizer(){}
	
	/**
	 * Sanitizes any object's String value to verify if it is present in the
	 * values provided in the {@code values} parameter.
	 *
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @param values
	 *            A list of values accepted to sanitize the given {@code value}.
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_NOT_A_CHOICE} : if the value's String
	 *             representation is not a choice given in the {@code values}'
	 *             list.</li>
	 *             </ul>
	 * @throws IllegalArgumentException
	 *             Thrown if the values list is empty. Please use
	 *             {@link TextSanitizer} if you want a String representation of
	 *             your value without any testing beforehand.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, String... values)
			throws BadFormatException, IllegalArgumentException{
		return EnumSanitizer.sanitizeValue(value,
				new ArrayList<>(Arrays.asList(values)));
	}
	
	/**
	 * Sanitizes any object's String value to verify if it is present in the
	 * values provided in the {@code values} parameter.
	 *
	 * @param value
	 *            Any object that its String value is a parsable number within
	 *            the bounds of the Integer primitive object.
	 * @param values
	 *            A list of values accepted to sanitize the given {@code value}.
	 * @return The String object as if the
	 *         {@link TextSanitizer#sanitizeValue(Object)} method was used.
	 * @throws BadFormatException
	 *             Thrown if the value's format is not matching this sanitizer's
	 *             purpose. Possible codes are :
	 *             <ul>
	 *             <li>{@link #FORMAT_NOT_A_CHOICE} : if the value's String
	 *             representation is not a choice given in the {@code values}'
	 *             list.</li>
	 *             </ul>
	 * @throws IllegalArgumentException
	 *             Thrown if the values list is empty. Please use
	 *             {@link TextSanitizer} if you want a String representation of
	 *             your value without any testing beforehand.
	 * @see TextSanitizer
	 */
	public static String sanitizeValue(Object value, List<String> values)
			throws BadFormatException, IllegalArgumentException{
		
		if(values.size() == 0){
			throw new IllegalArgumentException(
					"You need to provide at least one value to the values list!");
		}
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(!values.contains(stringValue)){
			throw new BadFormatException("The value " + stringValue
					+ " is not a choice in the list!", FORMAT_NOT_A_CHOICE);
		}
		
		return stringValue;
		
	}
	
	/**
	 * Extracts a list of values based on the format of the String given.<h3>
	 * Example of good formats</h3>
	 * <ul>
	 * <li>{@code Value1|Value2|Value3}</li>
	 * <li>{@code Value1 | Value2 | Value3}</li>
	 * <li>{@code |}</li>
	 * <li>{@code ||}</li>
	 * <li>{@code |||||||}</li>
	 * <li>{@code Value \| still 1 with a pipe}</li>
	 * <li>{@code Value \| 1 | Value \| 2}</li>
	 * <li>{@code Value \\| 1 with 1 backslash and pipe symbol}</li>
	 * </ul>
	 * <h3>Example of bad formats</h3>
	 * <ul>
	 * <li>{@code | Startup pipes | are | evil}</li>
	 * <li>{@code Same | with | ending pipes |}</li>
	 * </ul>
	 * 
	 * @param stringValue
	 *            The values separated by {@code "|"}.
	 * @return A list containing all the values separated for simplicity of
	 *         retrieval.
	 * @throws BadFormatException
	 *             Thrown if the stringValue's format is not matching the
	 *             separation format. The error code will be
	 *             {@link #FORMAT_NOT_ACCEPTED}.
	 * @see #extractEnumFromString(String, char)
	 * @see #verifyStringFormat(String, char)
	 */
	public static List<String> extractEnumFromString(String stringValue)
			throws BadFormatException{
		return EnumSanitizer.extractEnumFromString(stringValue, '|');
	}
	
	/**
	 * Extracts a list of values based on the format of the String given. This
	 * method supports for a different separator (can only be one character),
	 * given in the {@code separator} parameter.<h3>Example of good formats
	 * (with the separator {@code '|'})</h3>
	 * <ul>
	 * <li>{@code Value1|Value2|Value3}</li>
	 * <li>{@code Value1 | Value2 | Value3}</li>
	 * <li>{@code |}</li>
	 * <li>{@code ||}</li>
	 * <li>{@code |||||||}</li>
	 * <li>{@code Value \| still 1 with a pipe}</li>
	 * <li>{@code Value \| 1 | Value \| 2}</li>
	 * <li>{@code Value \\| 1 with 1 backslash and pipe symbol}</li>
	 * </ul>
	 * <h3>Example of bad formats (with the separator {@code '|'})</h3>
	 * <ul>
	 * <li>{@code | Startup pipes | are | evil}</li>
	 * <li>{@code Same | with | ending pipes |}</li>
	 * </ul>
	 * 
	 * @param stringValue
	 *            The values separated by the separator as declared in the
	 *            parameter {@code separator}.
	 * @param separator
	 *            The separator to use when splitting values.
	 * @return A list containing all the values separated for simplicity of
	 *         retrieval.
	 * @throws BadFormatException
	 *             Thrown if the stringValue's format is not matching the
	 *             separation format. The error code will be
	 *             {@link #FORMAT_NOT_ACCEPTED}.
	 * @see #verifyStringFormat(String, char)
	 */
	public static List<String> extractEnumFromString(String stringValue,
			char separator) throws BadFormatException{
		
		// Verify that string is of format "[...]| [...] | [...]" while allowing single choice enums.
		// ~ Resetting stringValue here was not necessary, but this will make it future-proof ~
		stringValue = EnumSanitizer.verifyStringFormat(stringValue, separator);
		
		String pSep = Utils.protectRegexChar(separator);
		
		if(stringValue.matches("^\\s*(\\\\*" + pSep + ")+\\s*$")){
			
			ArrayList<String> values = new ArrayList<>();
			values.add(stringValue);
			
			return values;
			
		}
		else{
			
			String expNonProtectedSeparator;
			
			if(separator == '\\'){
				expNonProtectedSeparator = "(?<!\\\\)\\\\(?!\\\\)";
			}
			else{
				expNonProtectedSeparator = "(?<!\\\\)" + pSep;
			}
			
			String[] possibleValues = stringValue.trim().split(
					"\\s*" + expNonProtectedSeparator + "\\s*");
			
			ArrayList<String> values = new ArrayList<>();
			
			for(String possibleValue : possibleValues){
				if(separator == '\\'){
					values.add(possibleValue.replaceAll("(\\\\*)\\\\", "$1"));
				}
				else{
					values.add(possibleValue.replaceAll("\\\\" + pSep,
							String.valueOf(separator)));
				}
			}
			
			// Remove duplicate while keeping the order of the values
			LinkedHashSet<String> hs = new LinkedHashSet<>(values);
			values.clear();
			values.addAll(hs);
			
			return values;
			
		}
		
	}
	
	/**
	 * Verify the format of a given string to see if it's splittable via
	 * {@link #extractEnumFromString(String, char)}. <h3>Example of good formats
	 * (with the separator {@code '|'})</h3>
	 * <ul>
	 * <li>{@code Value1|Value2|Value3}</li>
	 * <li>{@code Value1 | Value2 | Value3}</li>
	 * <li>{@code |}</li>
	 * <li>{@code ||}</li>
	 * <li>{@code |||||||}</li>
	 * <li>{@code Value \| still 1 with a pipe}</li>
	 * <li>{@code Value \| 1 | Value \| 2}</li>
	 * <li>{@code Value \\| 1 with 1 backslash and pipe symbol}</li>
	 * </ul>
	 * <h3>Example of bad formats (with the separator {@code '|'})</h3>
	 * <ul>
	 * <li>{@code | Startup pipes | are | evil}</li>
	 * <li>{@code Same | with | ending pipes |}</li>
	 * </ul>
	 * 
	 * @param stringValue
	 *            The values separated by the separator as declared in the
	 *            parameter {@code separator}.
	 * @param separator
	 *            The separator to use when splitting values.
	 * @return {@code true} if the String given is splittable via the extract
	 *         logic, {@code false} otherwise.
	 * @throws BadFormatException
	 *             Thrown if the stringValue's format is not matching the
	 *             separation format. The error code will be
	 *             {@link #FORMAT_NOT_ACCEPTED}.
	 */
	protected static String verifyStringFormat(String stringValue,
			char separator) throws BadFormatException{
		
		// Please see https://regex101.com/r/FrVwfk for an interactive testing session for this regex.
		// Make sure to use the latest version on this website (click the v1 button to check).
		
		String pSep = Utils.protectRegexChar(separator);
		
		String expAnyNonBreakOrSep = "[^\\n" + pSep + "]*";
		String expAnyNonSpaceOrSep = "([^\\r\\n\\t\\f\\v "
				+ (separator == '\\' ? "" : pSep) + "]|\\\\+" + pSep + ")";
		
		String expOnlySeparators = pSep + "+";
		
		String expValidWord = expAnyNonBreakOrSep + expAnyNonSpaceOrSep
				+ expAnyNonBreakOrSep;
		String expAdditionalValidWords = pSep + expValidWord;
		
		String expValidWordsAsEnum = expValidWord + "("
				+ expAdditionalValidWords + ")*";
		
		String expEnumFormat = expOnlySeparators + "|" + expValidWordsAsEnum;
		
		return TextRegexSanitizer.sanitizeValue(stringValue, expEnumFormat);
		
	}
	
}
