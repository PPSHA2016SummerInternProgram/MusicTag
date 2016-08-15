package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

public interface SearchService {

	/**
	 * Search artist and release and recording. Return the first 20 results for
	 * each type.
	 * 
	 * @param key
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchAll(String key, int perPage) throws IOException;

	/**
	 * Search one page for artist.
	 * 
	 * @param key
	 * @param currPage
	 * @param perPage
	 * @return
	 */
	Map<String, Object> searchArtist(String key, int currPage, int perPage) throws IOException;

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
