package com.paypal.musictag.service;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;


public interface ReleaseService {
	
	Map<String, Object> vote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;
}
