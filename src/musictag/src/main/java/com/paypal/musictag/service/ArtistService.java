package com.paypal.musictag.service;

import java.util.Map;

public interface ArtistService {
	public Map<String, Object> basicInfo(String gid) throws Exception;

	public Map<String, Object> releaseGroup(String artistGid) throws Exception;
}
