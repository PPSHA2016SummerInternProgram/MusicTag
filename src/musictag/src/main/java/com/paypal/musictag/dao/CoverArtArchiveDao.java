package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface CoverArtArchiveDao {
	public Map<String, Object> releaseCover(String releaseGid) throws IOException;
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException;
}
