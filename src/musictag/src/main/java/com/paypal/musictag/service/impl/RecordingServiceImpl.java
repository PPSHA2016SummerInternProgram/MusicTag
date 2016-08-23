package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import com.paypal.musictag.dao.usingdb.RecordingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.paypal.musictag.dao.RecordingDao;
import com.paypal.musictag.service.RecordingService;

@Service("RecordingServiceImpl")
public class RecordingServiceImpl implements RecordingService {

    @Autowired
    private RecordingDao recordingDaoWSImpl;
    @Autowired
	private RecordingMapper recordingMapper;
    @Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;


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

    public Map<String, Object> lyric(String recordingGId) throws IOException{
        String work_mbid = recordingMapper.getWorkMBID(UUID.fromString(recordingGId));

        Query searchQuery = new Query(Criteria.where("work_mbid").is(work_mbid));
        return  mongoTemplate.findOne(searchQuery, Map.class, "lyric");
    }
}
