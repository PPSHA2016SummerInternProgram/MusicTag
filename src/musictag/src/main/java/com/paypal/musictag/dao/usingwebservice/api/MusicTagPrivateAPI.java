package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.util.MusicTagUtil;

public final class MusicTagPrivateAPI {
	private static final Logger logger = LoggerFactory.getLogger(MusicTagPrivateAPI.class);

	private MusicTagPrivateAPI() {

	}

	private static final String URL = MusicTagUtil.getProperties().getProperty("musicbrainzPrivateURL");

	public static Map<String, Object> getArtistCommonsImage(String artistGid) throws NetConnectionException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("commons-image").toString();
		Document doc = null;
		try {
			doc = Jsoup.connect(requestUrl).get();
		} catch (IOException e) {
			logger.error(null, e);
			throw new NetConnectionException();
		}
		Elements imgs = doc.select(".picture > img");
		String commonsImgUrl = "";
		if (imgs != null && imgs.first() != null) {
			commonsImgUrl = imgs.first().attr("src");
		}
		map.put("commons-img", commonsImgUrl);
		return map;
	}

	public static Map<String, Object> getArtistWikiProfle(String artistGid) throws NetConnectionException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("wikipedia-extract").toString();
		Document doc;
		try {
			doc = Jsoup.connect(requestUrl).get();
		} catch (IOException e) {
			logger.error(null, e);
			throw new NetConnectionException();
		}
		Elements wikis = doc.select("div.wikipedia-extract-body.wikipedia-extract-collapse");
		String wikiExtract = "";
		if (wikis != null && wikis.first() != null) {
			wikiExtract = wikis.first().outerHtml();
		}
		map.put("wikipedia-extract", wikiExtract);
		return map;
	}
}
