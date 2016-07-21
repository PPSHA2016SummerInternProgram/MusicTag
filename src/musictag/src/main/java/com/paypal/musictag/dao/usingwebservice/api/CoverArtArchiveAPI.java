package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.paypal.musictag.util.MusicTagUtil;

/**
 * This class uses coverartarchive api to get cover for release/release group.
 * request url is
 * http://coverartarchive.org/{release|release-group}/gid
 * 
 * @author shilzhang
 *
 */
public final class CoverArtArchiveAPI {
	private static final String URL = "http://coverartarchive.org/";

	public static Map<String, Object> sendRequest(String subUrl, String gid) throws IOException {
		StringBuilder url = new StringBuilder(URL).append(subUrl).append(gid);

		URL requestUrl = new URL(url.toString());
		String json = MusicTagUtil.getJsonFromURL(requestUrl);

		return MusicTagUtil.jsontoMap(json);
	}

}
