package com.paypal.musictag.mongo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoArtistException;

public final class ArtistConnector extends MongoConnector {

	private final static String artistTableName = MongoCollectionName.LAST_FM_ARTIST.getName();
	private final static String artistNotFoundTableName = MongoCollectionName.LAST_FM_ARTIST_NOT_FOUND.getName();

	private final static String offsetFile = "logs/album.log";

	private int offset = 0;
	private int cacheAmount = 100;
	private FindIterable<Document> artists = null;
	private MongoCursor<Document> cursor = null;
	private int seq = 0;

	public ArtistConnector() throws UnknownHostException {
		super(artistTableName, artistNotFoundTableName);

		readOffset();
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

			saveOffset();

			offset += cacheAmount;

			if (!cursor.hasNext()) {
				throw new NoArtistException();
			}
		}

		Map<String, Object> artist = cursor.next();
		artist.put("seq", ++seq);

		return artist;
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

	private void readOffset() {
		Scanner reader = null;
		try {
			reader = new Scanner(new File(offsetFile));
			offset = reader.nextInt();
			seq += offset;
		} catch (Exception e) {
			// just empty, nothing to handle
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	private void saveOffset() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(offsetFile));
			writer.write(String.valueOf(offset));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
}
