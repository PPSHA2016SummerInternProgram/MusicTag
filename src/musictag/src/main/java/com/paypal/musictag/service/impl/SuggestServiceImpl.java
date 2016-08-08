package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.paypal.musictag.service.SuggestService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("suggestServiceImpl")
public class SuggestServiceImpl implements SuggestService {
	private static final String solrArtistSuggestURL = MusicTagUtil.getProperties().getProperty("solrArtistBase")
			+ "suggest";
	private static final String solrReleaseSuggestURL = MusicTagUtil.getProperties().getProperty("solrReleaseBase")
			+ "suggest";
	private static final String solrRecordingSuggestURL = MusicTagUtil.getProperties().getProperty("solrRecordingBase")
			+ "suggest";

	private static Logger logger = LoggerFactory.getLogger(SuggestServiceImpl.class);

	@Override
	public Map<String, Object> suggestAll(String key, int suggestCountPerGroup) {
		Map<String, Object> result = new HashMap<>();

		try {
			result.put("artist", suggestArtists(key, suggestCountPerGroup));
		} catch (IOException e) {
			logger.error(
					"Requesting suggestion for artist. Key: " + key + "suggestCountPerGroup: " + suggestCountPerGroup,
					e);
		}

		try {
			result.put("release", suggestReleases(key, suggestCountPerGroup));
		} catch (IOException e) {
			logger.error(
					"Requesting suggestion for release. Key: " + key + "suggestCountPerGroup: " + suggestCountPerGroup,
					e);
		}

		try {
			result.put("recording", suggestRecordings(key, suggestCountPerGroup));
		} catch (Exception e) {
			logger.error("Requesting suggestion for recording. Key: " + key + "suggestCountPerGroup: "
					+ suggestCountPerGroup, e);
		}

		return result;
	}

	@Override
	public Map<String, Object> suggestArtists(String key, int suggestCount) throws IOException {
		String url = buildSuggestQuery(solrArtistSuggestURL, key, suggestCount);
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));

	}

	@Override
	public Map<String, Object> suggestReleases(String key, int suggestCount) throws IOException {
		String url = buildSuggestQuery(solrReleaseSuggestURL, key, suggestCount);
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));

	}

	@Override
	public Map<String, Object> suggestRecordings(String key, int suggestCount) throws IOException {
		String url = buildSuggestQuery(solrRecordingSuggestURL, key, suggestCount);
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));
	}

	private String buildSuggestQuery(String url, String key, int suggestCount) {
		if (key == null) {
			key = "";
		}

		StringBuilder sb = new StringBuilder(url).append("?q=").append(key);
		if (suggestCount > 0) {
			sb.append("&suggest.count=").append(suggestCount);
		}

		return sb.toString();
	}

}
