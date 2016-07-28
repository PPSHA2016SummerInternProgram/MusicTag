package com.paypal.musictag.service;

import java.io.IOException;
import java.util.Map;

public interface ReleaseGroupService {
    Map<String, Object> releases(String releaseGroupId) throws IOException;
}
