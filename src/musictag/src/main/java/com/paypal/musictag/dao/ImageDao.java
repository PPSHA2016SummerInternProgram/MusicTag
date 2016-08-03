package com.paypal.musictag.dao;

import java.util.List;
import java.util.Map;

public interface ImageDao {

	List<Map<String, Object>> artistImagesFromLastfm(String artistGid);

	List<Map<String, Object>> albumImagesFromLastfm(String albumGid);

	/**
	 * Save coverart image information. This mbid can be artistGid, releaseGid,
	 * etc.
	 * 
	 * @param mbid
	 * @return
	 */
	void saveImageInfoToCoverart(String mbid, Map<String, Object> info);

	/**
	 * Mark the mbid as not found.
	 * 
	 * @param mbid
	 */
	void saveNotFoundToCoverart(String mbid);

	/**
	 * Is the mbid exist in not-found table.
	 * 
	 * @param mbid
	 * @return
	 */
	boolean isNotFound(String mbid);

	/**
	 * Get coverart image information. This mbid can be artistGid, releaseGid,
	 * etc.
	 * 
	 * @param mbid
	 * @return
	 */
	Map<String, Object> imageInfoFromCoverart(String mbid);
}
