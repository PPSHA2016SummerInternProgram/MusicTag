package com.paypal.musictag.dao.mongo.mapper;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "distribution")
public class Distribution {

	private String id;

	private String type;

	private String description;

	private List<Object> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

}
