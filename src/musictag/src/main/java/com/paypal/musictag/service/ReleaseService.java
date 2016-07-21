package com.paypal.musictag.service;

import java.util.Map;


public interface ReleaseService {
	
	Map<String, Object> vote(String gid) throws Exception;
}
