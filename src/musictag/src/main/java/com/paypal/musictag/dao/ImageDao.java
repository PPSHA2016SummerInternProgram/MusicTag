package com.paypal.musictag.dao;

import java.util.List;
import java.util.Map;

public interface ImageDao {

	List<Map<String, Object>> artistImagesFromLastfm(String artistGid);

	List<Map<String, Object>> albumImagesFromLastfm(String albumGid);
}
