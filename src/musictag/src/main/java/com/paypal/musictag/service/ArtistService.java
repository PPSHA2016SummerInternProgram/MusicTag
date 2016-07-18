package com.paypal.musictag.service;

import java.util.Map;

public interface ArtistService {
    
    Map<String, Object> profile(String gid) throws Exception;
    
    Map<String, Object> relLinks(String gid) throws Exception;
    
    Map<String, Object> image(String gid) throws Exception;
    
	Map<String, Object> basicInfo(String gid) throws Exception;

	Map<String, Object> releaseGroup(String artistGid) throws Exception;
}
