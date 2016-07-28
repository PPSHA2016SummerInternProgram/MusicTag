package com.paypal.musictag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author shilzhang
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NetConnectionException extends NetworkException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595227915533134233L;

	public NetConnectionException(String msg){
		super(msg);
	}
	
	public NetConnectionException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
}
