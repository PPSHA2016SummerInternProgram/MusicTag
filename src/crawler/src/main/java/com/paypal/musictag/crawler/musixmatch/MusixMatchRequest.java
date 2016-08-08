package com.paypal.musictag.crawler.musixmatch;

import org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.Map;

public class MusixMatchRequest {
	/**
	 * @param method
	 * @param params
	 * @return
	 * @throws IOException
     */
	public static String sendRequest(String method, Map<String, Object> params) {
		StringBuffer buffer = new StringBuffer();

		String apiUrl = Constants.API_URL + Constants.API_VERSION
				+ Constants.URL_DELIM + method + UrlHelper.getURLQueryString(params);

		BufferedReader in = null;
		try {
			URL url = new URL(apiUrl);

			in = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));
			String str;

			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}

		return buffer.toString();
	}
}
