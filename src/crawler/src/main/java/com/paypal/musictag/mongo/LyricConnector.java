package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.paypal.musictag.crawler.musixmatch.Constants;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.util.CrawlerUtil;

import static com.mongodb.client.model.Filters.eq;

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

	private List<Map<String, Object>> findOneInFoundTable(String work_mbid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put(Constants.WORK_MBID, work_mbid);
		FindIterable<Document> cursor = foundCollection.find(filter);
		return fillDBObjectList(cursor);
	}

	private List<Map<String, Object>> findOneInNotFoundTable(String work_mbid) {
		BasicDBObject filter = new BasicDBObject();
		filter.put(Constants.WORK_MBID, work_mbid);
		FindIterable<Document> cursor = notFoundCollection.find(filter);
		return fillDBObjectList(cursor);
	}

	public void insertOneIntoFoundTable(Map<String, Object> one) {
		if (findOneInFoundTable(String.valueOf(one.get(Constants.WORK_MBID))).isEmpty()) {
			foundCollection.insertOne(new Document(one));
		}
	}

	public void insertOneIntoNotFoundTable(Map<String, Object> one) {
		if (findOneInNotFoundTable(String.valueOf(one.get(Constants.WORK_MBID))).isEmpty()) {
			notFoundCollection.insertOne(new Document(one));
		}
	}

	public void updateOneInFoundTable(Map<String, Object> one, boolean upsert) {
		updateOne(foundCollection, one, upsert);
	}

	public void updateOneInFoundTable(Map<String, Object> one) {
	    updateOne(foundCollection, one, false);
	}

	public void updateOneInNotFoundTable(Map<String, Object> one, boolean upsert) {
		updateOne(notFoundCollection, one, upsert);
	}

	public void updateOneInNotFoundTable(Map<String, Object> one) {
	    updateOne(notFoundCollection, one, false);
	}

	private void updateOne(MongoCollection<Document> table, Map<String, Object> one, boolean upsert) {
		UpdateOptions op = new UpdateOptions();
		op.upsert(upsert);
		table.updateOne(eq(Constants.WORK_MBID, one.get(Constants.WORK_MBID)),
				new Document("$set", new Document(one)), op);
	}

	public UpdateResult replaceOneInFoundTable(Map<String, Object> one, boolean upsert) {
		return replaceOne(foundCollection, one, upsert);
	}

	public UpdateResult replaceOneInFoundTable(Map<String, Object> one) {
	    return replaceOne(foundCollection, one, false);
	}

	public UpdateResult replaceOneInNotFoundTable(Map<String, Object> one, boolean upsert) {
		return replaceOne(notFoundCollection, one, upsert);
	}

	public UpdateResult replaceOneInNotFoundTable(Map<String, Object> one) {
	    return replaceOne(notFoundCollection, one, false);
	}

	private UpdateResult replaceOne(MongoCollection<Document> table, Map<String, Object> one, boolean upsert) {
		UpdateOptions op = new UpdateOptions();
		op.upsert(upsert);
		return table.replaceOne(eq(Constants.WORK_MBID, one.get(Constants.WORK_MBID)),
				new Document(new Document(one)), op);
	}
}
