package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.util.CrawlerUtil;

public class LyricConnector extends MongoConnector implements IGeneralConnector{
    private final static String lyricTableName = MongoCollectionName.LYRIC.getName();
	private final static String lyricNotFoundTableName = MongoCollectionName.LYRIC_NOT_FOUND.getName();

	private final static String offsetFile = "/tmp/mongo.lyric.log";

    private int offset = 0;
	private int cacheAmount = 10000;
	private FindIterable<Document> works = null;
	private MongoCursor<Document> cursor = null;
	private int seq = 0;

	private void readOffset() {
		offset = CrawlerUtil.readInt(offsetFile);
		seq += offset;
	}

	private void saveOffset() {
		CrawlerUtil.writeInt(offsetFile, offset);
	}

    public LyricConnector() throws UnknownHostException {
		super(lyricTableName, lyricNotFoundTableName);

		readOffset();
	}

	@Override
	public synchronized Map<String, Object> next() throws NoNextException {
		if(cursor == null || !cursor.hasNext()) {
			BasicDBObject keys = new BasicDBObject();
			keys.append("mbid", 1);
			keys.append("from", 1);
			keys.append("_id", 1);

			works = getFoundCollection().find().projection(keys).skip(offset).limit(cacheAmount);
            cursor = works.iterator();

			saveOffset();

			offset += cacheAmount;

			if(!cursor.hasNext()) {
				throw new NoNextException();
			}

		}
		Map<String, Object> work = cursor.next();
		work.put("seq", ++seq);

        return work;
	}

	public static void main(String args[]) throws UnknownHostException, NoNextException {
		LyricConnector lc = new LyricConnector();
        lc.next();
	}

}
