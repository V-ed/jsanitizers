package io.github.ved.jsanitizers;

/**
 * Utility that sanitizes text objects. handles {@code null} objects too.
 */
public interface TextSanitizer {
	
	/**
	 * Sanitizes any object to a String value (using the object's
	 * {@code toString()} method).
	 * 
	 * @param value
	 *            Any object to extract a String value out of its
	 *            {@code toString()} method.
	 * @return The String value of the object given as parameter. If the value
	 *         is {@code null}, it returns an empty String ({@code ""}).
	 */
	static String sanitizeValue(Object value){
		
		if(value == null)
			return "";
		
		return value.toString().trim();
		
	}
	
}
