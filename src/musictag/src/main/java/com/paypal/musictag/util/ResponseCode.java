package com.paypal.musictag.util;

import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;

public enum ResponseCode {
	NOT_PROVIDED(100, "not provided"),
	// success start from 200
	SUCCESS(200, "success"),

	// error code start from 400
	ERR_NOT_FOUND(400, "not found"),

	NET_NOT_AVAILABLE(401, "Cannot access that URL."),

	NET_CONTENT_NOT_FOUND(402, "No content found."),

	BAD_JSON_FORMAT(403, "Data is not format in JSON."),

	BAD_URL_FORMAT(404, "The URL is wrong."),

	BAD_HTTP_PROTOCOL(405, "Http type may be wrong(get or post?)"),

	BAD_ARGUMENT(406, "Illegal argument found.");

	private static final Logger logger = LoggerFactory.getLogger(ResponseCode.class);

	private final int id;
	private final String msg;

	private ResponseCode(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	public int getValue() {
		return id;
	};

	public String getMsg() {
		return msg;
	};

	public static ResponseCode getResponseCode(Exception e) {

		if (e instanceof NetConnectionException) {
			return NET_NOT_AVAILABLE;
		} else if (e instanceof NetContentNotFoundException) {
			return NET_CONTENT_NOT_FOUND;
		} else if (e instanceof JsonMappingException) {
			return BAD_JSON_FORMAT;
		} else if (e instanceof MalformedURLException) {
			return BAD_URL_FORMAT;
		} else if (e instanceof ProtocolException) {
			return BAD_HTTP_PROTOCOL;
		} else if (e instanceof IllegalArgumentException) {
			return BAD_ARGUMENT;
		}

		logger.error("Cannot get response code for this exception.", e);
		return null;
	}
}
