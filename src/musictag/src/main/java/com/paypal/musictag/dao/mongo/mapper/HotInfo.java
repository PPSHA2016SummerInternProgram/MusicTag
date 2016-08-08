package com.paypal.musictag.dao.mongo.mapper;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statistics")
public class HotInfo {

	private String type;

	private List<Object> data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

}
