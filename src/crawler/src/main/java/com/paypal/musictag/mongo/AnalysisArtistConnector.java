package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.Document;

public class AnalysisArtistConnector extends MongoConnector {

	public AnalysisArtistConnector() throws UnknownHostException {
		super("ArtistWorkCount", "");
	}

	public void save(Map<String, Object> data) {
		// System.out.println(data.get("gid"));
		data.put("gid", String.valueOf(data.get("gid")));
		getFoundCollection().insertOne(new Document(data));
	}

	static public void main(String[] argv) throws UnknownHostException {

		AnalysisArtistConnector a = new AnalysisArtistConnector();

	}
}
