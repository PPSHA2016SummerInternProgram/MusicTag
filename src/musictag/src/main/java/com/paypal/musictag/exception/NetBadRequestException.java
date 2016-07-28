package com.paypal.musictag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Throw when server returns 400 bad request.
 * Occurs when the url is not valid or gid is not valid.
 * 
 * @author shilzhang
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class NetBadRequestException extends NetworkException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7685523826580643332L;

	public NetBadRequestException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NetBadRequestException(String msg) {
		super(msg);
	}
	
}
