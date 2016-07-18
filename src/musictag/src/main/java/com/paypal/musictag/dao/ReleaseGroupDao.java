package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface ReleaseGroupDao {
	Map<String, Object> releases(String releaseGroupId) throws IOException;
}
