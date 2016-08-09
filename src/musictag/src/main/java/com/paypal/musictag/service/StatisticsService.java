package com.paypal.musictag.service;

import java.util.Map;

public interface StatisticsService {

	Map<String, Object> artistListeners(String gid);

	Map<String, Object> artistPlaycount(String gid);

	Map<String, Object> releaseListeners(String gid);

	Map<String, Object> releasePlaycount(String gid);

	Map<String, Object> recordingListeners(String gid);

	Map<String, Object> recordingPlaycount(String gid);
}
