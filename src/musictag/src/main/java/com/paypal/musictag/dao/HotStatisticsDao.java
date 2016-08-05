package com.paypal.musictag.dao;

import java.util.Map;

public interface HotStatisticsDao {

	Map<String, Object> artistListeners();

	Map<String, Object> artistPlaycount();

	Map<String, Object> releaseListeners();

	Map<String, Object> releasePlaycount();

	Map<String, Object> recordingListeners();

	Map<String, Object> recordingPlaycount();
}
