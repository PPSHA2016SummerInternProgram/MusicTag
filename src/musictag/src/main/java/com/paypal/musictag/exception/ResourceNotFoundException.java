package com.paypal.musictag.exception;

/**
 * Throw when resource files can't load.
 * @author shilzhang
 *
 */
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3077877153658132768L;

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	
}
