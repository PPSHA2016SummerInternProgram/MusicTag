package com.paypal.musictag.mongo;

import java.util.Map;

import com.paypal.musictag.exception.NoNextException;

public interface IGeneralConnector {

	Map<String, Object> next() throws NoNextException;

}
