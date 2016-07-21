package com.paypal.musictag.service;

import java.util.Map;

public interface ReleaseGroupService {
    Map<String, Object> releases(String releaseGroupId) throws Exception;
}
