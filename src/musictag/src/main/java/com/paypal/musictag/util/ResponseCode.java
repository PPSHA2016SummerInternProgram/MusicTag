package com.paypal.musictag.util;

public enum ResponseCode {
	NOT_PROVIDED(100, "not provided"),                    
	// success start from 200
	SUCCESS(200, "success"),
	
	//error code start from 400
	ERR_NOT_FOUND(400, "not found");
	
	private final int id;
	private final String msg;
	private ResponseCode(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	
	public int getValue(){return id;};
	public String getMsg(){return msg;};
}
