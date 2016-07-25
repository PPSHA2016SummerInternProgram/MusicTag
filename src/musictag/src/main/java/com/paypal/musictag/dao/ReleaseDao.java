package com.paypal.musictag.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public interface ReleaseDao {

	
	/**
	 * Find vote_values for release.
	 * 
	 * @param ReleaseGid
	 * @return
	 * @throws ProtocolException 
	 * @throws MalformedURLException 
	 * @throws JsonMappingException 
	 * @throws NetContentNotFoundException 
	 * @throws NetConnectionException 
	 * @throws IOException
	 */
	Map<String, Object> vote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

	Map<String, Object> artistinfo(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;
	
	Map<String, Object> releasevote(String gid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;
}
