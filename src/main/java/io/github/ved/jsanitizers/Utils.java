package io.github.ved.jsanitizers;

/**
 * Utils class for various utilities that may be useful for sanitization.
 */
public interface Utils {
	
	/**
	 * Protects a character from being used in a regex with the protection char
	 * "\".
	 * 
	 * @param character
	 *            The character to protect.
	 * @return A string with the protected separator if it was potentially
	 *         dangerous in a regex, of the direct String representation of the
	 *         char if no protection was needed.
	 */
	static String protectRegexChar(char character){
		
		if("<([{\\^-=$!|]})?*+.>".indexOf(character) != -1){
			return String.format("\\%s", character);
		}
		else{
			return String.valueOf(character);
		}
		
	}
	
}
