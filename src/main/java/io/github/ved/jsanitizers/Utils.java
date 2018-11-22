package io.github.ved.jsanitizers;

public interface Utils {
	
	static String protectSeparator(char separator){
		
		if("<([{\\^-=$!|]})?*+.>".indexOf(separator) != -1){
			return String.format("\\%s", separator);
		}
		else{
			return String.valueOf(separator);
		}
		
	}
	
}
