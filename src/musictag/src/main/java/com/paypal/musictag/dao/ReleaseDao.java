package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface ReleaseDao {

	
	/**
	 * Find vote_values for release.
	 * 
	 * @param ReleaseGid
	 * @return
	 * @throws IOException
	 */
	Map<String, Object> vote(String gid) throws IOException;

	Map<String, Object> artistinfo(String gid) throws IOException;
	
	Map<String, Object> releasevote(String gid) throws IOException;
}
