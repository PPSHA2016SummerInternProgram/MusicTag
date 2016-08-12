package com.paypal.musictag.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.usingdb.CountryAndDateMapper;
import com.paypal.musictag.service.SuggestService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("suggestServiceImpl")
public class SuggestServiceImpl implements SuggestService {
	private static final String solrArtistSuggestURL = MusicTagUtil.getProperties().getProperty("artistBase")
			+ "suggest";
	private static final String solrReleaseSuggestURL = MusicTagUtil.getProperties().getProperty("releaseBase")
			+ "suggest";
	private static final String solrRecordingSuggestURL = MusicTagUtil.getProperties().getProperty("recordingBase")
			+ "suggest";

	private static Logger logger = LoggerFactory.getLogger(SuggestServiceImpl.class);

	@Autowired
	private CountryAndDateMapper countryAndDateMapper;

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

	private Map<String, Object> suggestArtists(String key, int suggestCount) throws IOException {
		String url = buildSuggestQuery(solrArtistSuggestURL, key, suggestCount);
		return MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));

	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getSuggestions(String key, Map<String, Object> objects) {
		Map<String, Object> suggest = (Map<String, Object>) objects.get("suggest");
		Map<String, Object> suggester = (Map<String, Object>) suggest.get("suggester");
		Map<String, Object> value = (Map<String, Object>) suggester.get(key);
		return (List<Map<String, Object>>) value.get("suggestions");
	}

	private List<UUID> fillGids(List<Map<String, Object>> suggestions) {
		List<UUID> gids = new ArrayList<>();
		for (Map<String, Object> release : suggestions) {
			String gid = String.valueOf(release.get("payload"));
			gids.add(UUID.fromString(gid));
		}
		return gids;
	}

	private void addCountryAndDate(List<Map<String, Object>> suggestions, List<Map<String, Object>> countryAndDate) {
		for (Map<String, Object> release : suggestions) {
			String gid = String.valueOf(release.get("payload"));
			for (Map<String, Object> meta : countryAndDate) {
				if (String.valueOf(meta.get("gid")).equals(gid)) {
					release.put("country-date", meta);
				}
			}
		}
	}

	private Map<String, Object> suggestReleases(String key, int suggestCount) throws IOException {
		String url = buildSuggestQuery(solrReleaseSuggestURL, key, suggestCount);
		Map<String, Object> releases = MusicTagUtil.jsontoMap(
				MusicTagUtil.getJsonFromURLWithoutProxy(new URL(MusicTagUtil.encodeURIComponent(url.toString()))));
		List<Map<String, Object>> suggestions = getSuggestions(key, releases);
		List<UUID> gids = fillGids(suggestions);
		List<Map<String, Object>> countryAndDate = countryAndDateMapper.releaseCountryAndDate(gids);
		addCountryAndDate(suggestions, countryAndDate);
		return releases;

	}

	private Map<String, Object> suggestRecordings(String key, int suggestCount) throws IOException {
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
