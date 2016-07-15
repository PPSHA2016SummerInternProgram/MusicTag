package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class MusicTagPrivateAPI {
	private static final String URL = "http://10.24.53.72:5000/";

	public static Map<String, Object> getArtistCommonsImage(String artistGid) throws MalformedURLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("commons-image").toString();
		Document doc = Jsoup.connect(requestUrl).get();
		Element img = doc.select(".picture > img").first();
		String commonsImgUrl = img.attr("src");
		System.out.println("commonsImgUrl: "+commonsImgUrl);
		map.put("commons-img", commonsImgUrl);
		return map;
	}

	public static Map<String, Object> getArtistWikiProfle(String artistGid) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("wikipedia-extract").toString();
		System.out.println(requestUrl);
		Document doc = Jsoup.connect(requestUrl).get();
		System.out.println(doc.outerHtml());
		Element wiki = doc.select("div.wikipedia-extract-body.wikipedia-extract-collapse").first();
		String wikiExtract = wiki.outerHtml();
		map.put("wikipedia-extract", wikiExtract);
		return map;
	}
}
