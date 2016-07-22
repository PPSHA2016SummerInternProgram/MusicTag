package com.paypal.musictag.service;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public interface CoverArtArchiveService {
	Map<String, Object> releaseCover(String releaseGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException;
	Map<String, Object> releaseGroupCover(String releaseGroupGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException;
}
