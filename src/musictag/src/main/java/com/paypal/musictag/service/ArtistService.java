package com.paypal.musictag.service;

import java.util.Map;

public interface ArtistService {
    Map<String, Object> findById(String id) throws Exception;
}
