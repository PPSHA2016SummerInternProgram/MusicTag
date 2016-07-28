package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface CoverArtArchiveDao {
	Map<String, Object> releaseCover(String releaseGid) throws IOException;
	Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException;
}
