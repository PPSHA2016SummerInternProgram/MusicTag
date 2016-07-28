package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.RecordingDao;
import com.paypal.musictag.service.RecordingService;

@Service("RecordingServiceImpl")
public class RecordingServiceImpl implements RecordingService {

    @Autowired
    private RecordingDao recordingDaoWSImpl;

    public Map<String, Object> basic(String recordingId) throws IOException{
        return recordingDaoWSImpl.basic(recordingId);
    }

    public Map<String, Object> releases(String recordingId) throws IOException{
        return recordingDaoWSImpl.releases(recordingId);
    }

    public Map<String, Object> workArtistRels(String recordingId) throws IOException{
        return recordingDaoWSImpl.workArtistRels(recordingId);
    }

    public Map<String, Object> full(String recordingId) throws IOException{
        return recordingDaoWSImpl.full(recordingId);
    }
}
