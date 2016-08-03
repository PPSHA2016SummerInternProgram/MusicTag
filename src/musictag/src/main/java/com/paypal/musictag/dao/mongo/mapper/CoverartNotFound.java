package com.paypal.musictag.dao.mongo.mapper;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coverart.notfound")
public class CoverartNotFound {

	private String gid;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

}
