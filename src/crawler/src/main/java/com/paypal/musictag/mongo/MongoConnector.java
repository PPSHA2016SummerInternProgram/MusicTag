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

enum MongoCollectionName {
	LAST_FM_ARTIST("lastfm.artist"), LAST_FM_ARTIST_NOT_FOUND("lastfm.artist.notfound"),
	LAST_FM_ALBUM("lastfm.album"), LAST_FM_ALBUM_NOT_FOUND("lastfm.album.notfound"),
	LAST_FM_TRACK("lastfm.track"), LAST_FM_TRACK_NOT_FOUND("lastfm.track.notfound"),
	LYRIC("lyric"), LYRIC_NOT_FOUND("lyric.not_found");

	private String collectionName;

	MongoCollectionName(String name) {
		collectionName = name;
	}

	public String getName() {
		return collectionName;
	}
}

class MongoConnector {
	private final static String host = "10.24.53.72";
	private final static int port = 27017;
	private final static String databaseName = "music-tag";

	private final MongoClient mongoClient;
	private final MongoDatabase mongoDatabase;
	private final String foundCollectionName;
	private final String noExistCollectionName;
	private final MongoCollection<Document> foundCollection;
	private final MongoCollection<Document> notFoundCollection;

	public MongoConnector(String foundCollectionName, String notFoundCollectionName) throws UnknownHostException {

		this.foundCollectionName = foundCollectionName;
		this.noExistCollectionName = notFoundCollectionName;

		mongoClient = new MongoClient(host, port);
		mongoDatabase = mongoClient.getDatabase(databaseName);
		foundCollection = mongoDatabase.getCollection(this.foundCollectionName);
		notFoundCollection = mongoDatabase.getCollection(this.noExistCollectionName);

	}

	MongoCollection<Document> getFoundCollection() {
		return foundCollection;
	}

	/**
	 * Is the gid exist in found table.
	 * 
	 * @param gid
	 * @return
	 */
	public boolean isAlreadyFound(String gid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("gid", gid);
		FindIterable<Document> cursor = foundCollection.find(filter);
		if (!fillDBObjectList(cursor).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Is the gid exist in not-found table.
	 * 
	 * @param gid
	 * @return
	 */
	public boolean isAlreadyNotFound(String gid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("gid", gid);
		FindIterable<Document> cursor = notFoundCollection.find(filter);
		if (!fillDBObjectList(cursor).isEmpty()) {
			return true;
		}
		return false;
	}

	private List<Map<String, Object>> findOneInFoundTable(String gid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("gid", gid);
		FindIterable<Document> cursor = foundCollection.find(filter);
		return fillDBObjectList(cursor);
	}

	private List<Map<String, Object>> findOneInNotFoundTable(String gid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put("gid", gid);
		FindIterable<Document> cursor = notFoundCollection.find(filter);
		return fillDBObjectList(cursor);
	}

	public void insertOneIntoFoundTable(Map<String, Object> one) {
		if (findOneInFoundTable(String.valueOf(one.get("gid"))).isEmpty()) {
			foundCollection.insertOne(new Document(one));
		}
	}

	public void insertOneIntoNotFoundTable(Map<String, Object> one) {
		if (findOneInNotFoundTable(String.valueOf(one.get("gid"))).isEmpty()) {
			notFoundCollection.insertOne(new Document(one));
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

	// public static void main(String[] args) throws UnknownHostException {
	// MongoConnector connection = new MongoConnector("lastfm.artist");
	// List<?> artists =
	// connection.findArtist("881a3dca-7c80-487e-8360-3427d2ad18bd");
	// System.out.println(artists);
	// }s
}
