package com.paypal.musictag.statistics;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.mongo.AlbumConnector;
import com.paypal.musictag.mongo.ArtistConnector;
import com.paypal.musictag.mongo.IGeneralConnector;
import com.paypal.musictag.mongo.StatisticConnector;
import com.paypal.musictag.mongo.TrackConnector;

final public class HotDistribution {

	enum EntityType {
		ARTIST("artist"), ALBUM("album"), TRACK("track");
		private String name;

		EntityType(String n) {
			name = n;
		}

		public String getName() {
			return name;
		}
	}

	enum HotType {
		LISTENERS("listeners"), PLAYCOUNT("playcount");
		private String name;

		HotType(String n) {
			name = n;
		}

		public String getName() {
			return name;
		}
	}

	private IGeneralConnector connector = null;

	public void distribution(EntityType entity, HotType hot, int window, int maxAmount) throws UnknownHostException {
		Map<Integer, Integer> hotMap = new HashMap<>();
		if (entity == null) {
			return;
		}
		connector = null;
		switch (entity) {
		case ARTIST:
			connector = new ArtistConnector();
			break;
		case ALBUM:
			connector = new AlbumConnector();
			break;
		case TRACK:
			connector = new TrackConnector();
			break;
		default:
			return;
		}

		try {
			int i = 0;
			while (true) {
				i++;
				if (i % 1000 == 0) {
					System.out.println("Finished: " + i);
				}
				Map<String, Object> item = connector.next();
				int hotValue = getHot(entity, item, hot);
				int key = (int) (Math.log(hotValue + 1) * window);
				int currHot = 0;
				if (hotMap.containsKey(key)) {
					currHot = hotMap.get(key);
				}
				hotMap.put(key, currHot + 1);
				if (maxAmount > 0 && i > maxAmount) {
					break;
				}
			}
		} catch (NoNextException e) {
		}

		List<List<Integer>> data = new ArrayList<>(hotMap.size());
		for (Entry<Integer, Integer> pair : hotMap.entrySet()) {
			List<Integer> arr = new ArrayList<>();
			arr.add(pair.getKey());
			arr.add(pair.getValue());
			data.add(arr);
		}
		StatisticConnector statistic = new StatisticConnector();
		statistic.save(entity.getName() + "-" + hot.getName(), data);
	}

	private int getHot(EntityType entity, Map<String, Object> item, HotType hot) {
		if (hot == null) {
			return 0;
		}
		Object value = null;
		if (entity == EntityType.ARTIST) {
			Object obj = item.get("stats");
			if (!(obj instanceof Map)) {
				return 0;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> stats = (Map<String, Object>) obj;
			value = stats.get(hot.getName());
		} else {
			value = item.get(hot.getName());
		}
		try {
			return Integer.parseInt(String.valueOf(value));
		} catch (Exception e) {
			return 0;
		}
	}

	static public void main(String[] args) throws UnknownHostException {

		HotDistribution h = new HotDistribution();
		if (args.length < 2) {
			System.out.println("At least two params are needed.");
			return;
		}
		EntityType entity = null;
		HotType hot = null;

		String entityType = args[0];
		String hotType = args[1];
		// String entityType = "artist";
		// String entityType = "album";
		// String entityType = "track";
		// String hotType = "listeners";
		// String hotType = "playcount";

		int window = 100;
		int amount = 100000;
		if (args.length >= 3) {
			window = Integer.parseInt(args[2]);
		}
		if (args.length >= 4) {
			amount = Integer.parseInt(args[3]);
		}

		if ("artist".equals(entityType)) {
			entity = EntityType.ARTIST;
		} else if ("album".equals(entityType)) {
			entity = EntityType.ALBUM;
		} else if ("track".equals(entityType)) {
			entity = EntityType.TRACK;
		} else {
			System.out.println("The first param needs to be [artist | album | track].");
			return;
		}

		if ("listeners".equals(hotType)) {
			hot = HotType.LISTENERS;
		} else if ("playcount".equals(hotType)) {
			hot = HotType.PLAYCOUNT;
		} else {
			System.out.println("The second param needs to be [listeners | playamount].");
			return;
		}

		System.out.println(
				"EntityType: " + entityType + ", HotType: " + hotType + ", Window: " + window + ", Amount: " + amount);

		h.distribution(entity, hot, window, amount);
	}
}
