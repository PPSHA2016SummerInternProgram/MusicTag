package com.paypal.musictag.service.impl;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.service.ReleaseGroupService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ReleaseGroupDao;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@Service("releaseGroupServiceImpl")
public class ReleaseGroupServiceImpl implements ReleaseGroupService{
    @Autowired
    private ReleaseGroupDao releaseGroupDaoWSImpl;

    @Override
    public Map<String, Object> releases(String releaseGroupId) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException{
        return releaseGroupDaoWSImpl.releases(releaseGroupId);
    }
}
