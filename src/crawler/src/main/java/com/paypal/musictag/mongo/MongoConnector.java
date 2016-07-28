package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

final public class MongoConnector {
	private final static String host = "10.24.53.72";
	private final static int port = 27017;
	private final static String databaseName = "music-tag";

	private final MongoClient mongoClient;
	private final MongoDatabase mongoDatabase;
	private final String collectionName;
	private final MongoCollection<Document> collection;

	public MongoConnector(String collectionName) throws UnknownHostException {

		this.collectionName = collectionName;

		mongoClient = new MongoClient(host, port);
		mongoDatabase = mongoClient.getDatabase(databaseName);
		collection = mongoDatabase.getCollection(this.collectionName);

	}

	public List<Map<String, Object>> findArtist(String gid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("gid", gid);
		FindIterable<Document> cursor = collection.find(filter);
		return fillDBObjectList(cursor);
	}

	public void insertArtist(Map<String, Object> artist) {
		if (findArtist(String.valueOf(artist.get("gid"))).isEmpty()) {
			collection.insertOne(new Document(artist));
		}
	}

	/**
	 * Convert a DBCursor to a list.
	 * 
	 * @param cursor
	 * @return
	 */
	private List<Map<String, Object>> fillDBObjectList(FindIterable<Document> cursor) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Document document : cursor) {
			list.add(document);
		}
		return list;
	}

	public static void main(String[] args) throws UnknownHostException {
		MongoConnector connection = new MongoConnector("lastfm.artist");
		List<?> artists = connection.findArtist("881a3dca-7c80-487e-8360-3427d2ad18bd");
		System.out.println(artists);
	}
}
