package io.github.ved.jsanitizers;

import io.github.ved.jsanitizers.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public interface EnumSanitizer {
	
	int FORMAT_NOT_ACCEPTED = TextRegexSanitizer.FORMAT_NOT_MATCHING_PATTERN;
	int FORMAT_NOT_A_CHOICE = 2;
	
	static String sanitizeValue(Object value, String... values)
			throws BadFormatException, IllegalArgumentException{
		return EnumSanitizer.sanitizeValue(value,
				new ArrayList<>(Arrays.asList(values)));
	}
	
	static String sanitizeValue(Object value, List<String> values)
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
	
	static List<String> extractEnumFromString(String stringValue)
			throws BadFormatException{
		return EnumSanitizer.extractEnumFromString(stringValue, '|');
	}
	
	static List<String> extractEnumFromString(String stringValue, char separator)
			throws BadFormatException{
		
		// Verify that string is of format "[...]| [...] | [...]" while allowing single choice enums.
		// ~ Resetting stringValue here was not necessary, but this will make it future-proof ~
		stringValue = EnumSanitizer.verifyStringFormat(stringValue, separator);
		
		String pSep = Utils.protectSeparator(separator);
		
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
	
	static String verifyStringFormat(String stringValue, char separator)
			throws BadFormatException{
		
		// Please see https://regex101.com/r/FrVwfk for an interactive testing session for this regex.
		// Make sure to use the latest version on this website (click the v1 button to check).
		
		String pSep = Utils.protectSeparator(separator);
		
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
