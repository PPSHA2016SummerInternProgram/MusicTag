package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.service.RecordingService;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.RecordingDao;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@Service("RecordingServiceImpl")
public class RecordingServiceImpl implements RecordingService {

    @Autowired
    private RecordingDao recordingDaoWSImpl;

    public Map<String, Object> basic(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException {
        return recordingDaoWSImpl.basic(recordingId);
    }

    public Map<String, Object> releases(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException {
        return recordingDaoWSImpl.releases(recordingId);
    }

    public Map<String, Object> workArtistRels(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException {
        return recordingDaoWSImpl.workArtistRels(recordingId);
    }

    public Map<String, Object> full(String recordingId) throws NetConnectionException, MalformedURLException, JsonMappingException, NetContentNotFoundException, ProtocolException {
        return recordingDaoWSImpl.full(recordingId);
    }
}
