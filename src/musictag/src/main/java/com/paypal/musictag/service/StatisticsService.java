package com.paypal.musictag.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

	Map<String, Object> distributionDetail(String gid, String type);

	Map<String, Object> distribution();
	
	Map<String, Object> distributionScores(String gid);
	
	Map<String, Object> releaseInfo(String gid);
	
	//Map<String, Object> distributionScores(String gid);

	Map<String, Object> artistListeners(String gid);

	Map<String, Object> artistPlaycount(String gid);

	Map<String, Object> releaseListeners(String gid);

	Map<String, Object> releasePlaycount(String gid);

	Map<String, Object> recordingListeners(String gid);

	Map<String, Object> recordingPlaycount(String gid);

	Map<String, Object> artistCreditCount(String artistGid);

	//List<Map<String, Object>> artistArea(String artistGid);
	List<Map<String, Object>> artistAreaCount(String artistGid);
	
	List<Map<String, Object>> artistAreaDetails(String artistGid, String area);
	
	List<Map<String, Object>> artistEdit(String artistGid);

	List<Map<String, Object>> artistReleaseYearlyDist(String artistGid);
	
	Map<String, Object> artistLyricist(String artistGid);
	
	Map<String, Object> artistComposer(String artistGid);
}
