package com.paypal.musictag.dao.mongo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.dao.mongo.mapper.HotInfo;
import com.paypal.musictag.dao.mongo.mapper.HotRank;
import com.paypal.musictag.dao.mongo.mapper.LastfmArtist;

@Service("hotStatisticsDaoImpl")
public class HotStatisticsDaoImpl implements HotStatisticsDao {

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	private Map<String, Object> getData(String type, String gid) {
		Query searchQuery = new Query(Criteria.where("type").is(type));
		HotInfo hotInfo = mongoTemplate.findOne(searchQuery, HotInfo.class);
		Map<String, Object> map = new HashMap<>();
		if (hotInfo != null) {
			map.put("data", hotInfo.getData());
		}

		searchQuery = new Query(Criteria.where("gid").is(gid));
		LastfmArtist artist = mongoTemplate.findOne(searchQuery, LastfmArtist.class);
		if (artist != null) {

			Map<String, Object> stats = artist.getStats();
			if (stats != null) {
				int amount = Integer.parseInt(String.valueOf(stats.get(type.split("-")[1])));
				map.put("amount", amount);
				map.put("rank", getHotRank(type, amount));
				Long totalAmount = mongoTemplate.count(null, Long.class, "lastfm." + type.split("-")[0]);
				map.put("total", totalAmount.intValue());
			}
		}

		return map;
	}

	private int getHotRank(String type, int hot) {
		String collectionName = "statistics." + type.replaceAll("-", ".");
		Query searchQuery = new Query(Criteria.where("hot").is(hot));
		HotRank hotRank = mongoTemplate.findOne(searchQuery, HotRank.class, collectionName);
		if (hotRank != null) {
			return hotRank.getAmount();
		}
		return 0;
	}

	@Override
	public Map<String, Object> artistListeners(String gid) {
		return getData("artist-listeners", gid);
	}

	@Override
	public Map<String, Object> artistPlaycount() {
		return getData("artist-playcount", "");
	}

	@Override
	public Map<String, Object> releaseListeners() {
		return getData("album-listeners", "");
	}

	@Override
	public Map<String, Object> releasePlaycount() {
		return getData("album-playcount", "");
	}

	@Override
	public Map<String, Object> recordingListeners() {
		return getData("track-listeners", "");
	}

	@Override
	public Map<String, Object> recordingPlaycount() {
		return getData("track-playcount", "");
	}

}
