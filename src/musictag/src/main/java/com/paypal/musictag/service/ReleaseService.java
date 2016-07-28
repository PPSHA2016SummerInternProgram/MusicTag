package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;


public interface ReleaseService {
	
	Map<String, Object> vote(String gid) throws IOException;
	
	Map<String, Object> artistinfo(String gid) throws IOException;
	
	Map<String, Object> releasevote(String gid) throws IOException;
}
