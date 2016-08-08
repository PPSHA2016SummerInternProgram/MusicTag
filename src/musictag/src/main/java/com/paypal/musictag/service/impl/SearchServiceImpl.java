package com.paypal.musictag.service.impl;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.service.SearchService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {

	private static Logger logger = Logger.getLogger(SearchServiceImpl.class);

	@Override
	public Map<String, Object> searchAll(String key, int perPage) {
		Map<String, Object> artist = null;
		Map<String, Object> release = null;
		Map<String, Object> recording = null;
		int defaultPerPage = perPage;
		try {
			artist = searchArtist(key, 0, defaultPerPage);
		} catch (Exception e) {
			logger.error(null, e);
		}
		try {
			release = searchRelease(key, 0, defaultPerPage);
		} catch (Exception e) {
			logger.error(null, e);
		}
		try {
			recording = searchRecording(key, 0, defaultPerPage);
		} catch (Exception e) {
			logger.error(null, e);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("artist", artist);
		result.put("release", release);
		result.put("recording", recording);
		return result;
	}

	@Override
	public Map<String, Object> searchArtist(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("artist", key, currPage, perPage);
	}

	@Override
	public Map<String, Object> searchRelease(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("release", key, currPage, perPage);
	}

	@Override
	public Map<String, Object> searchRecording(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("recording", key, currPage, perPage);
	}

	private Map<String, Object> search(String entityType, String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, ProtocolException,
			MalformedURLException, JsonMappingException {
		StringBuilder url = new StringBuilder(String.valueOf(MusicTagUtil.getProperties().get(entityType + "Base")));
//		url.append("?wt=json&");
//		url.append("q=entity_type:").append(entityType).append(" AND (").append("name:\"").append(key).append("\" ")
//				.append(key).append(")").append("&");
		url.append(entityType).append("?q=").append(key).append("&");
		url.append("start=").append(currPage * perPage).append("&");
		url.append("rows=").append(perPage).append("&");
		System.out.println(url.toString());
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));

	}

}
