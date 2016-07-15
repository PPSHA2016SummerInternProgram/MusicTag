package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.paypal.musictag.util.MusicTagUtil;

/**
 * This class uses coverartarchive api to get cover for release/release group.
 * request url is
 * http://coverartarchive.org/{release|release-group}/gid/[front|end]
 * 
 * @author shilzhang
 *
 */
public final class CoverArtArchiveServiceAPI {
	private static final String URL = "http://coverartarchive.org/";

	public static Map<String, Object> sendRequest(String subUrl, String gid) throws IOException {
		StringBuffer url = new StringBuffer(URL).append(subUrl).append(gid);

		URL requestUrl = new URL(url.toString());
		System.out.println(requestUrl);
		String json = MusicTagUtil.getJsonFromURL(requestUrl);

		Map<String, Object> map = MusicTagUtil.jsontoMap(json);
		System.out.println(map);
		return map;
	}

}
