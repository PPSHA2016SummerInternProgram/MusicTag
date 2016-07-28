package com.paypal.musictag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Throws when get 404 from server.
 * 
 * @author shilzhang
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NetContentNotFoundException extends NetworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4074552847599431570L;

	public NetContentNotFoundException(String msg) {
		super(msg);
	}

	public NetContentNotFoundException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
