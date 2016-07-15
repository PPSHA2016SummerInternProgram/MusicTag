package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.CoverArtArchiveDao;
import com.paypal.musictag.service.CoverArtArchiveService;

@Service("coverArtArchiveServiceImpl")
public class CoverArtArchiveServiceImpl implements CoverArtArchiveService {

	@Autowired
	private CoverArtArchiveDao coverArtArchiveWSImpl;

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws IOException {
		return coverArtArchiveWSImpl.releaseCover(releaseGid);
	}

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException {
		return coverArtArchiveWSImpl.releaseGroupCover(releaseGroupGid);
	}

}
