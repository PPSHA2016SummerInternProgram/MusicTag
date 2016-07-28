package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;

import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.util.MusicTagUtil;

public final class MusicTagPrivateAPI {

	private MusicTagPrivateAPI() {

	}

	private static final String URL = MusicTagUtil.getProperties().getProperty("musicbrainzPrivateURL");

	/**
	 * 
	 * @param artistGid
	 * @return
	 * @throws NetConnectionException
	 * @throws NetContentNotFoundException
	 * @throws NetBadRequestException 
	 */
	public static Map<String, Object> getArtistCommonsImage(String artistGid)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("commons-image").toString();
		Document doc = null;
		try {
			doc = Jsoup.connect(requestUrl).get();
		} catch (SocketTimeoutException | MalformedURLException e) {
			throw new NetConnectionException("url: " + requestUrl, e);
		} catch (HttpStatusException e) {
			if(e.getStatusCode() == HttpStatus.BAD_REQUEST.value()){
				throw new NetBadRequestException("url: " + requestUrl, e);
			}else{
				throw new NetContentNotFoundException("url: " + requestUrl, e);
			}
		} catch (IOException e) {
			throw new NetContentNotFoundException("url: " + requestUrl, e);
		}
		Elements imgs = doc.select(".picture > img");
		if (imgs == null || imgs.first() == null) {
			throw new NetContentNotFoundException("url: " + requestUrl);
		}
		map.put("commons-img", imgs.first().attr("src"));
		return map;
	}

	/**
	 * 
	 * @param artistGid
	 * @return
	 * @throws NetConnectionException
	 * @throws NetContentNotFoundException
	 * @throws NetBadRequestException 
	 */
	public static Map<String, Object> getArtistWikiProfle(String artistGid)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException {
		Map<String, Object> map = new HashMap<String, Object>();
		String requestUrl = new StringBuilder(URL).append("artist/").append(artistGid).append("/")
				.append("wikipedia-extract").toString();
		Document doc;
		Jsoup.connect(requestUrl);
		try {
			doc = Jsoup.connect(requestUrl).get();
		} catch (SocketTimeoutException | MalformedURLException e) {
			throw new NetConnectionException("url: " + requestUrl, e);
		} catch (HttpStatusException e) {
			if(e.getStatusCode() == HttpStatus.BAD_REQUEST.value()){
				throw new NetBadRequestException("url: " + requestUrl, e);
			}else{
				throw new NetContentNotFoundException("url: " + requestUrl, e);
			}
		} catch (IOException e) {
			throw new NetContentNotFoundException("url: " + requestUrl, e);
		}
		Elements wikis = doc.select("div.wikipedia-extract-body.wikipedia-extract-collapse");
		if (wikis == null || wikis.first() == null) {
			throw new NetContentNotFoundException("url: " + requestUrl);
		}
		map.put("wikipedia-extract", wikis.first().outerHtml());
		return map;
	}
}
