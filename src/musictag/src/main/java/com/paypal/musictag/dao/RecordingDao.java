package com.paypal.musictag.dao;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public interface RecordingDao {
    Map<String, Object> basic(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException;

    Map<String, Object> releases(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException;

    Map<String, Object> workArtistRels(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException;

    /**
     * @param recordingId the MBID of the recording
     * @return Map object contains basic info, ratings, related releases, and work & artist relationships,
     *         where work further contains artist relationships
     * @throws IOException
     */
    Map<String, Object> full(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException;

}
