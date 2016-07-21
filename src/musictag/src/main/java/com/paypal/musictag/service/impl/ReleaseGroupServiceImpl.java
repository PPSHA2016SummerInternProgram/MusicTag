package com.paypal.musictag.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.service.ReleaseGroupService;
import com.paypal.musictag.dao.ReleaseGroupDao;

@Service("releaseGroupServiceImpl")
public class ReleaseGroupServiceImpl implements ReleaseGroupService{
    @Autowired
    private ReleaseGroupDao releaseGroupDaoWSImpl;

    @Override
    public Map<String, Object> releases(String releaseGroupId) throws Exception {
        return releaseGroupDaoWSImpl.releases(releaseGroupId);
    }
}
