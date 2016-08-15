package com.paypal.musictag.dao.usingwebservice.api;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.util.MusicTagUtil;

final public class LastFmAPI {

	private LastFmAPI() {

	}

	private static String BASE_URL = MusicTagUtil.getProperties().getProperty("lastfmWebServiceURL");
	private static String API_KEY = MusicTagUtil.getProperties().getProperty("lastfmApiKey");
	private static String ARTIST_SIMILAR_URL = BASE_URL + "?format=json&method=artist.getsimilar&api_key=" + API_KEY
			+ "&mbid=";

	public static Map<String, Object> similarArtist(String gid)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, ProtocolException,
			MalformedURLException, JsonMappingException {
		System.out.println(ARTIST_SIMILAR_URL + gid);
		return MusicTagUtil.jsontoMap(MusicTagUtil.getJsonFromURLWithoutProxy(new URL(ARTIST_SIMILAR_URL + gid)));
	}
}
