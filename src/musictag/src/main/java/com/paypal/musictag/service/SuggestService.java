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
	
}
