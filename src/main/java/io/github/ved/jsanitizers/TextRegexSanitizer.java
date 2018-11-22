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
				isInverted, false);
	}
	
	static String sanitizeValue(Object value, String regexToMatch,
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
