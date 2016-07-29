package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoAlbumException;
import com.paypal.musictag.util.CrawlerUtil;

public class AlbumConnector extends MongoConnector {

	private final static String albumTableName = MongoCollectionName.LAST_FM_ALBUM.getName();
	private final static String albumNotFoundTableName = MongoCollectionName.LAST_FM_ALBUM_NOT_FOUND.getName();

	private final static String offsetFile = "logs/track.log";

	private int offset = 0;
	private int cacheAmount = 100;
	private FindIterable<Document> albums = null;
	private MongoCursor<Document> cursor = null;
	private int seq = 0;

	public AlbumConnector() throws UnknownHostException {
		super(albumTableName, albumNotFoundTableName);

		readOffset();
	}

	public String getAblumTableName() {
		return albumTableName;
	}

	public String getAlbumNotFoundTablename() {
		return albumNotFoundTableName;
	}

	synchronized public Map<String, Object> nextAlbum() throws NoAlbumException {

		if (cursor == null || !cursor.hasNext()) {

			BasicDBObject key = new BasicDBObject();
			key.append("gid", 1);
			key.append("listeners", 1);
			key.append("_id", 0);

			albums = getFoundCollection().find().projection(key).skip(offset).limit(cacheAmount);
			cursor = albums.iterator();

			saveOffset();

			offset += cacheAmount;

			if (!cursor.hasNext()) {
				throw new NoAlbumException();
			}
		}

		Map<String, Object> album = cursor.next();
		album.put("seq", ++seq);

		return album;
	}

	private void readOffset() {
		offset = CrawlerUtil.readInt(offsetFile);
		seq += offset;
	}

	private void saveOffset() {
		CrawlerUtil.writeInt(offsetFile, offset);
	}

	public static void main(String[] args) throws UnknownHostException {
		AlbumConnector connector = new AlbumConnector();
		for (int i = 0; i < 10; i++) {
			try {
				Map<String, Object> album = connector.nextAlbum();
				System.out.println(album);
			} catch (NoAlbumException e) {
				e.printStackTrace();
			}
		}
	}
}
