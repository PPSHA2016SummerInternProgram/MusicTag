package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.paypal.musictag.util.MusicTagUtil;

/**
 * This class uses the music-brainz HTTP API to get data. If cannot get data,
 * throw an exception.
 * 
 * @author delliu
 */
public final class MusicTagServiceAPI {

	static final String URL = MusicTagUtil.getProperties().getProperty(
			"musicbrainzWebServiceURL");

	public static Map<String, Object> sendRequest(String subUrl,
			Map<String, String> params) throws IOException {

		// Build URL
		StringBuilder url = new StringBuilder(URL).append(subUrl).append("?");
		StringBuilder paramsStr = new StringBuilder("fmt=json&");
		if (params != null) {
			for (Entry<String, String> pair : params.entrySet()) {
				paramsStr.append(pair.getKey()).append("=")
						.append(pair.getValue()).append("&");
			}
		}
		String u = url.append(paramsStr).toString();
		URL obj = new URL(u);

		String json = MusicTagUtil.getJsonFromURL(obj);

		Map<String, Object> map = MusicTagUtil.jsontoMap(json);

		return map;
	}
}
