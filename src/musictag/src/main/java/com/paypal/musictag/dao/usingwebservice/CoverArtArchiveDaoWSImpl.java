package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.CoverArtArchiveDao;
import com.paypal.musictag.dao.usingwebservice.api.CoverArtArchiveAPI;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@Service("coverArtArchiveDaoWSImpl")
public class CoverArtArchiveDaoWSImpl implements CoverArtArchiveDao {

	@Override
	public Map<String, Object> releaseCover(String releaseGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException {
		return CoverArtArchiveAPI.sendRequest("release/", releaseGid);
	}

	@Override
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws JsonMappingException, NetConnectionException, NetContentNotFoundException, MalformedURLException, ProtocolException {
		return CoverArtArchiveAPI.sendRequest("release-group/", releaseGroupGid);
	}
}
