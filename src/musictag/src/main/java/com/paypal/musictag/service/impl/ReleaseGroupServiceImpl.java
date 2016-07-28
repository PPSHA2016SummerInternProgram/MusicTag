package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ReleaseGroupDao;
import com.paypal.musictag.service.ReleaseGroupService;

@Service("releaseGroupServiceImpl")
public class ReleaseGroupServiceImpl implements ReleaseGroupService{
    @Autowired
    private ReleaseGroupDao releaseGroupDaoWSImpl;

    @Override
    public Map<String, Object> releases(String releaseGroupId) throws IOException{
        return releaseGroupDaoWSImpl.releases(releaseGroupId);
    }
}
