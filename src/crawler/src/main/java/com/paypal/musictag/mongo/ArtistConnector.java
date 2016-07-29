package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoArtistException;

public final class ArtistConnector extends MongoConnector {

	private final static String artistTableName = MongoCollectionName.LAST_FM_ARTIST.getName();
	private final static String artistNotFoundTableName = MongoCollectionName.LAST_FM_ARTIST_NOF_FOUND.getName();

	private int offset = 10000;
	private int cacheAmount = 100;
	private FindIterable<Document> artists = null;
	private MongoCursor<Document> cursor = null;

	public ArtistConnector() throws UnknownHostException {
		super(artistTableName, artistNotFoundTableName);
	}

	public String getArtistTableName() {
		return artistTableName;
	}

	public String getArtistNotFoundTablename() {
		return artistNotFoundTableName;
	}

	/**
	 * Thread safe. Traverse the artist table, find the next artist.
	 * 
	 * @return
	 * @throws NoArtistException
	 */
	public synchronized Map<String, Object> nextArtist() throws NoArtistException {

		if (cursor == null || !cursor.hasNext()) {

			BasicDBObject key = new BasicDBObject();
			key.append("gid", 1);
			key.append("stats", 1);
			key.append("_id", 0);

			artists = getFoundCollection().find().projection(key).skip(offset).limit(cacheAmount);
			cursor = artists.iterator();

			offset += cacheAmount;

			if (!cursor.hasNext()) {
				throw new NoArtistException();
			}
		}

		return cursor.next();
	}

	public static void main(String[] args) throws UnknownHostException {
		ArtistConnector connector = new ArtistConnector();
		Map<?, ?> artist = null;
		try {
			for (int i = 0; i < 300; i++) {
				artist = connector.nextArtist();
				System.out.println(i + ": " + artist.get("gid"));
			}
		} catch (NoArtistException e) {
			e.printStackTrace();
		}
	}
}
