package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.paypal.musictag.util.MusicTagUtil;


public class MusicTagPrivateAPI {
	private static final String URL = MusicTagUtil.getProperties().getProperty("musicbrainzPrivateURL");

	public static Map<String, Object> getArtistCommonsImage(String artistGid) throws MalformedURLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("commons-image").toString();
		Document doc = Jsoup.connect(requestUrl).get();
		Elements imgs = doc.select(".picture > img");
		String commonsImgUrl = "";
		if (imgs != null && imgs.first() != null) {
			commonsImgUrl = imgs.first().attr("src");
		}
		map.put("commons-img", commonsImgUrl);
		return map;
	}

	public static Map<String, Object> getArtistWikiProfle(String artistGid) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("wikipedia-extract").toString();
		Document doc = Jsoup.connect(requestUrl).get();
		Elements wikis = doc.select("div.wikipedia-extract-body.wikipedia-extract-collapse");
		String wikiExtract = "";
		if(wikis != null && wikis.first() != null){
			wikiExtract = wikis.first().outerHtml();
		}
		map.put("wikipedia-extract", wikiExtract);
		return map;
	}
}
