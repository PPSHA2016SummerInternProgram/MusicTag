package com.paypal.musictag.service;

import java.util.Map;

public interface ArtistService {
	Map<String, Object> basicInfo(String gid) throws Exception;

	Map<String, Object> releaseGroup(String artistGid) throws Exception;
}
