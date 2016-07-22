package com.paypal.musictag.dao.usingwebservice;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.ArtistDao;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagPrivateAPI;
import com.paypal.musictag.dao.usingwebservice.api.MusicTagServiceAPI;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

@Service("artistDaoWSImpl")
public class ArtistDaoWSImpl implements ArtistDao {

	@Override
	public Map<String, Object> profile(String artistGid) throws NetConnectionException {
		return MusicTagPrivateAPI.getArtistWikiProfle(artistGid);
	}

	@Override
	public Map<String, Object> relLinks(String artistGid) throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		Map<String, String> params = new HashMap<>();
		params.put("inc", "url-rels");
		Map<String, Object> res = MusicTagServiceAPI.sendRequest("artist/" + artistGid, params);
		return res;
	}

	@Override
	public Map<String, Object> image(String artistGid) throws NetConnectionException {
		return MusicTagPrivateAPI.getArtistCommonsImage(artistGid);
	}

	@Override
	public Map<String, Object> basicInfo(String artistGid) throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException {
		Map<String, String> params = new HashMap<>();
		Map<String, Object> res = MusicTagServiceAPI.sendRequest("artist/" + artistGid, params);
		return res;
	}

	@Override
	public Map<String, Object> releaseGroup(String artistGid) throws NetConnectionException,
			NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("artist", artistGid);
		params.put("limit", "100");
		return MusicTagServiceAPI.sendRequest("release-group/", params);
	}
}
