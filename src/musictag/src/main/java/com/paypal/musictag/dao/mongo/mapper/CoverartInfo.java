package com.paypal.musictag.dao.mongo.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coverart")
public class CoverartInfo {

	private String gid;
	
	private Boolean success;
	
	private List<Map<String, Object>> images;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<Map<String, Object>> getImages() {
		return images;
	}

	public void setImages(List<Map<String, Object>> images) {
		this.images = images;
	}
	
}
