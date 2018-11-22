package io.github.ved.jsanitizers.exceptions;

/**
 *
 */
public class BadFormatException extends RuntimeException {
	
	private int code;
	
	/**
	 * default error code for any {@link BadFormatException}
	 */
	public static final int DEFAULT_ERROR_CODE = -1;
	
	/**
	 * Builds a default {@link BadFormatException} with the
	 * {@link BadFormatException#DEFAULT_ERROR_CODE} error code.
	 */
	public BadFormatException(){
		this(DEFAULT_ERROR_CODE);
	}
	
	/**
	 * Builds a {@link BadFormatException} with the error code given as
	 * parameter.
	 *
	 * @param errorCode
	 *            The error code that will be used for identification
	 *            purposes.
	 */
	public BadFormatException(int errorCode){
		super();
		
		this.code = errorCode;
	}
	
	/**
	 * Builds a default {@link BadFormatException} with the
	 * {@link BadFormatException#DEFAULT_ERROR_CODE} error code with a specified
	 * message.
	 *
	 * @param message
	 *            The message of this exception.
	 */
	public BadFormatException(String message){
		this(message, DEFAULT_ERROR_CODE);
	}
	
	/**
	 * Builds a {@link BadFormatException} with the message and the error code
	 * given as parameter.
	 *
	 * @param message
	 *            The message of this exception.
	 * @param errorCode
	 *            The error code that will be used for identification
	 *            purposes.
	 */
	public BadFormatException(String message, int errorCode){
		super(message);
		
		this.code = errorCode;
	}
	
	/**
	 * @return The error code specified when this {@link BadFormatException} was
	 *         created.
	 */
	public int getErrorCode(){
		return this.code;
	}
	
}
