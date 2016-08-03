package com.paypal.musictag.dao.mongo.mapper;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lastfm.album")
public class LastfmAlbum {

	private String gid;

	private String mbid;

	private String name;

	private Integer listeners;
	
	private Integer playcount;
	
	private List<Object> image;

	private String url;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getListeners() {
		return listeners;
	}

	public void setListeners(Integer listeners) {
		this.listeners = listeners;
	}

	public Integer getPlaycount() {
		return playcount;
	}

	public void setPlaycount(Integer playcount) {
		this.playcount = playcount;
	}

	public List<Object> getImage() {
		return image;
	}

	public void setImage(List<Object> image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
