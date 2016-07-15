package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

public interface CoverArtArchiveService {
	public Map<String, Object> releaseCover(String releaseGid) throws IOException;
	public Map<String, Object> releaseGroupCover(String releaseGroupGid) throws IOException;
}
