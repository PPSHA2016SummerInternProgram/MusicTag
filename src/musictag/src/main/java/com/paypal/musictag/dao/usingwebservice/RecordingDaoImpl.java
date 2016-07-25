package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.dao.RecordingDao;

@Service("recordingDaoImpl")
public class RecordingDaoImpl implements RecordingDao {

    @Override
    public Map<String, Object> basic(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException {
        return queryWithInc(recordingId, "ratings");
    }

    @Override
    public Map<String, Object> releases(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException {
        return queryWithInc(recordingId, "ratings+releases");
    }

    @Override
    public Map<String, Object> workArtistRels(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException {
        return queryWithInc(recordingId, "ratings+artist-rels+work-rels+work-level-rels");
    }

    @Override
    public Map<String, Object> full(String recordingId) throws NetContentNotFoundException, MalformedURLException, JsonMappingException, NetConnectionException, ProtocolException {
        return queryWithInc(recordingId, "ratings+artist-rels+work-rels+work-level-rels+releases");
    }

    private Map<String, Object> queryWithInc(String recordingId, String include) throws MalformedURLException, JsonMappingException, ProtocolException, NetConnectionException, NetContentNotFoundException {
        Map<String, String> params = new HashMap<>();
        params.put("inc", include);
        return MusicTagServiceAPI.sendRequest(entityPath(recordingId), params);
    }

    private String entityPath(String id) {
        return "recording/" + id;
    }
}
