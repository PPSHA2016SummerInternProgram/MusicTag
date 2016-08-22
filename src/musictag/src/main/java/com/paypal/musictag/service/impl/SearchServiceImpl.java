package com.paypal.musictag.service.impl;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.service.SearchService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {

	@Override
	public Map<String, Object> searchArtist(String key, int currPage, int perPage, MultiValueMap<String, String> params)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("artist", key, currPage, perPage, params);
	}

	@Override
	public Map<String, Object> searchRelease(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("release", key, currPage, perPage, null);
	}

	@Override
	public Map<String, Object> searchRecording(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("recording", key, currPage, perPage, null);
	}

	@Override
	public Map<String, Object> searchLyric(String key, int currPage, int perPage)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, JsonMappingException,
			ProtocolException, MalformedURLException {
		return search("lyric", key, currPage, perPage, null);
	}
	
	/**
	 * 
	 * @param allParams other request parameters used for artist.For now, mainly facet related params.
	 * @return
	 */
	private Map<String, Object> search(String entityType, String key, int currPage, int perPage, MultiValueMap<String, String> allParams)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, ProtocolException,
			MalformedURLException, JsonMappingException {
		StringBuilder url = new StringBuilder(String.valueOf(MusicTagUtil.getProperties().get(entityType + "Base")));
		url.append(entityType).append("?q=").append(key).append("&");
		url.append("start=").append(currPage * perPage).append("&");
		url.append("rows=").append(perPage).append("&");
		
		if(allParams != null){
			for(String param : allParams.keySet()){
				List<String> values = allParams.get(param);
				if(values != null){
					for(String value : values){
						url.append(param).append("=").append(value).append("&");
					}
				}
			}
		}
		System.out.println(url.toString());
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));

	}

}
