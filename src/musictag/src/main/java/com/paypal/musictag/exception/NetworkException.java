package com.paypal.musictag.exception;

import java.io.IOException;

/**
 * This is the user defined base expection for accessing data
 * from internet. 
 * 
 * @author shilzhang
 *
 */
class NetworkException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 347521018393753150L;

	public NetworkException(String msg){
		super(msg);
	}
	
	public NetworkException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
