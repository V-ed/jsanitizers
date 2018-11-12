package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public interface TextRegexSanitizer {
	
	int FORMAT_NOT_MATCHING_PATTERN = 1;
	
	static String sanitizeValue(Object value, String regexToMatch)
			throws BadFormatException, PatternSyntaxException{
		return TextRegexSanitizer.sanitizeValue(value, regexToMatch, false);
	}
	
	static String sanitizeValue(Object value, String regexToMatch,
			boolean isInverted) throws BadFormatException,
			PatternSyntaxException{
		return TextRegexSanitizer.sanitizeValue(value, regexToMatch,
				isInverted, true);
	}
	
	static String sanitizeValue(Object value, String regexToMatch,
			boolean isInverted, boolean shouldBox) throws BadFormatException,
			PatternSyntaxException{
		return TextRegexSanitizer.sanitizeValue(value, regexToMatch,
				isInverted, shouldBox, true);
	}
	
	static String sanitizeValue(Object value, String regexToMatch,
			boolean isInverted, boolean shouldBox, boolean shouldCheckPattern)
			throws BadFormatException, PatternSyntaxException{
		
		String stringValue = TextSanitizer.sanitizeValue(value);
		
		if(regexToMatch != null){
			
			if(shouldCheckPattern){
				// Test if Regex provided is valid
				Pattern.compile(regexToMatch);
			}
			
			if(shouldBox && !regexToMatch.matches("^\\^.*\\$$")){
				regexToMatch = "^" + regexToMatch + "$";
			}
			
			// Test regex and invert if we need to
			if(stringValue.matches(regexToMatch) == isInverted){
				throw new BadFormatException(
						"Value does not match the required pattern!",
						FORMAT_NOT_MATCHING_PATTERN);
			}
			
		}
		
		return stringValue;
		
	}
	
}
