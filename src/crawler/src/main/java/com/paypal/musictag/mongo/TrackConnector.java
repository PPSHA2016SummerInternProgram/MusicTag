package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoNextException;

public class TrackConnector extends MongoConnector implements IGeneralConnector {

	private final static String trackTableName = MongoCollectionName.LAST_FM_TRACK.getName();
	private final static String trackNotFoundTableName = MongoCollectionName.LAST_FM_TRACK_NOT_FOUND.getName();

	private int offset = 0;
	private int cacheAmount = 10000;
	private FindIterable<Document> tracks = null;
	private MongoCursor<Document> cursor = null;

	public TrackConnector() throws UnknownHostException {
		super(trackTableName, trackNotFoundTableName);
	}

	public String getTrackTableName() {
		return trackTableName;
	}

	public String getTrackNotFoundTableName() {
		return trackNotFoundTableName;
	}

	@Override
	public Map<String, Object> next() throws NoNextException {

		if (cursor == null || !cursor.hasNext()) {

			BasicDBObject key = new BasicDBObject();
			key.append("gid", 1);
			key.append("listeners", 1);
			key.append("playcount", 1);
			key.append("_id", 0);

			tracks = getFoundCollection().find().projection(key).skip(offset).limit(cacheAmount);
			cursor = tracks.iterator();

			if (!cursor.hasNext()) {
				throw new NoNextException();
			}
		}

		return cursor.next();
	}

}
