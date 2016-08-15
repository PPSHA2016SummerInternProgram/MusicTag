package com.paypal.musictag.dao.usingdb;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

public interface ArtistRelationMapper {
	List<Map<String, Object>> findArtistCreditLinkWithCount(@Param(value = "artistGid") UUID artistGid);

	List<Map<String, Object>> findArtistCreditNode(@Param(value="artistIds") List<Integer> ids);

	List<Map<String, Object>> getArtistCountInFirstLevel(@Param("artistGid") UUID artistGid);
	
	List<Map<String, Object>> getCooperationsOnRecordingOfArtists(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> getCooperationsOnReleaseOfArtists(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> findReleaseLyricistLink(@Param(value="artistGid") UUID artistGid);
	
	List<Map<String, Object>> getReleaseLyricCooperationsOfArtist(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> findReleaseComposerLink(@Param(value="artistGid") UUID artistGid);
	
	List<Map<String, Object>> getReleaseComposerCooperationsOfArtist(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> findRecordingLyricistLink(@Param(value="artistGid") UUID artistGid);
	
	List<Map<String, Object>> getRecordingLyricCooperationsOfArtists(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> findRecordingComposerLink(@Param(value="artistGid") UUID artistGid);

	List<Map<String, Object>> getRecordingComposerCooperationsOfArtists(@Param(value = "sid") Integer sid, @Param(value = "tid") Integer tid);
	
	List<Map<String, Object>> findArtistNode(@Param(value="artistIds") List<Integer> ids);
	
	int getReleaseCount(@Param(value = "artistGid") UUID artistGid);

	int getRecordingCount(@Param(value = "artistGid") UUID artistGid);

	List<Map<String, Object>> getArtistArea(@Param(value = "artistGid") UUID artistGid);
	
	List<Map<String, Object>> getArtistEdit(@Param(value = "artistGid") UUID artistGid);

	List<Map<String, Object>> getReleaseYearlyDist(@Param(value = "artistGid") UUID artistGid);
}
