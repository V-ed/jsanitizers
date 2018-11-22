package io.github.ved.jsanitizers;

/**
 * Utils class for various utilities that may be useful for sanitization.
 */
public interface Utils {
	
	/**
	 * Protects a character from being used in a regex with the protection char
	 * "%".
	 * 
	 * @param separator
	 *            The separator to protect.
	 * @return A string with the protected separator if it was potentially
	 *         dangerous in a regex, of the direct String representation of the
	 *         char if no protection was needed.
	 */
	static String protectSeparator(char separator){
		
		if("<([{\\^-=$!|]})?*+.>".indexOf(separator) != -1){
			return String.format("\\%s", separator);
		}
		else{
			return String.valueOf(separator);
		}
		
	}
	
}
