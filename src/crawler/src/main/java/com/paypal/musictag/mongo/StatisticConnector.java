package com.paypal.musictag.mongo;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class StatisticConnector extends MongoConnector {

	public StatisticConnector() throws UnknownHostException {
		super("statistics", "");
	}

	public void save(String type, List<List<Integer>> data) {
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		getFoundCollection().deleteMany(new Document(map));
		map.put("data", data);
		getFoundCollection().insertOne(new Document(map));
	}
}
