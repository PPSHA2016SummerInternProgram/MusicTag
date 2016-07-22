package com.paypal.musictag.service.impl;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.CoverArtArchiveDao;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.service.CoverArtArchiveService;

@Service("coverArtArchiveServiceImpl")
public class CoverArtArchiveServiceImpl implements CoverArtArchiveService {

	@Autowired
	private CoverArtArchiveDao coverArtArchiveDaoWSImpl;

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException {
		return coverArtArchiveDaoWSImpl.releaseCover(releaseGid);
	}

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException{
		return coverArtArchiveDaoWSImpl.releaseGroupCover(releaseGroupGid);
	}

}
