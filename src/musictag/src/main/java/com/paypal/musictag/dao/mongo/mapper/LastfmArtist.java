package com.paypal.musictag.dao.mongo.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lastfm.artist")
public class LastfmArtist {

	private String gid;

	private String mbid;

	private String name;

	private Map<String, Object> stats;

	private List<Object> image;

	private String url;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getStats() {
		return stats;
	}

	public void setStats(Map<String, Object> stats) {
		this.stats = stats;
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
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
