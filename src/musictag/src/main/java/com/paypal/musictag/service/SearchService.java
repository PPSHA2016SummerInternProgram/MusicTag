package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.MultiValueMap;

public interface SearchService {



	/**
	 * Search one page for artist.
	 * 
	 * @param key
	 * @param currPage
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchArtist(String key, int currPage, int perPage, MultiValueMap<String, String> params) throws IOException;

	/**
	 * Search one page for release.
	 * 
	 * @param key
	 * @param currPage
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchRelease(String key, int currPage, int perPage) throws IOException;

	/**
	 * Search one page for recording.
	 * 
	 * @param key
	 * @param currPage
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchRecording(String key, int currPage, int perPage) throws IOException;

	/**
	 * Search one page for recording.
	 *
	 * @param key
	 * @param currPage
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchLyric(String key, int currPage, int perPage) throws IOException;
}
