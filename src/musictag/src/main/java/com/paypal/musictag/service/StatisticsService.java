package com.paypal.musictag.service;

import java.util.Map;

public interface StatisticsService {

	Map<String, Object> artistListeners();

	Map<String, Object> artistPlaycount();

	Map<String, Object> releaseListeners();

	Map<String, Object> releasePlaycount();

	Map<String, Object> recordingListeners();

	Map<String, Object> recordingPlaycount();
	
	Map<String, Object> artistCreditCount(String artistGid);
}
