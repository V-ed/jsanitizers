package io.github.ved.jsanitizers;

public interface TextSanitizer {
	
	static String sanitizeValue(Object value){
		
		if(value == null)
			return "";
		
		return value.toString();
		
	}
	
}
