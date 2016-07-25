package com.paypal.musictag.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

public interface RecordingService {
    Map<String, Object> basic(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException;

    Map<String, Object> releases(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException;

    Map<String, Object> workArtistRels(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException;

    Map<String, Object> full(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException;
}
