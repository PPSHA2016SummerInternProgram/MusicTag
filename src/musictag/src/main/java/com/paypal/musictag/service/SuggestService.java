package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

public interface SuggestService {
	/**
	 * suggest artist release and recording based on key.
	 * 
	 * @param key suggestion key 
	 * @param suggestCountPerGroup max num of suggestions per group
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> suggestAll(String key, int suggestCountPerGroup) throws IOException;
	
	/**
	 * suggest artist names. 
	 * 
	 * @param key
	 * @param suggestCountPerGroup
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> suggestArtists(String key, int suggestCountPerGroup) throws IOException;
	
	/**
	 * suggest release names.
	 * @param key
	 * @param suggestCountPerGroup
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> suggestReleases(String key, int suggestCountPerGroup) throws IOException;
	
	/**
	 * suggest recording names.
	 * @param key
	 * @param suggestCountPerGroup
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> suggestRecordings(String key, int suggestCountPerGroup) throws IOException;
}
