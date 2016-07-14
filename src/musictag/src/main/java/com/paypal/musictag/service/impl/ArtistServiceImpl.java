package com.paypal.musictag.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.service.ArtistService;

@Service("artistServiceImpl")
public class ArtistServiceImpl implements ArtistService {

	@Autowired
	private ArtistDao artistDaoWSImpl;

	public Map<String, Object> basicInfo(String gid) throws Exception {
		return artistDaoWSImpl.basicInfo(gid);
	}

	public Map<String, Object> releaseGroup(String artistGid) throws Exception {
		return artistDaoWSImpl.releaseGroup(artistGid);
	}
}
