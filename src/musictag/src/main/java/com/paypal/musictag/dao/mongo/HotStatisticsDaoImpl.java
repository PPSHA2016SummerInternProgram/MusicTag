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

@Service("hotStatisticsDaoImpl")
public class HotStatisticsDaoImpl implements HotStatisticsDao {

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	private Map<String, Object> getData(String type) {
		Query searchQuery = new Query(Criteria.where("type").is(type));
		HotInfo hotInfo = mongoTemplate.findOne(searchQuery, HotInfo.class);
		Map<String, Object> map = new HashMap<>();
		if (hotInfo != null) {
			map.put("data", hotInfo.getData());
		}
		return map;
	}

	@Override
	public Map<String, Object> artistListeners() {
		return getData("artist-listeners");
	}

	@Override
	public Map<String, Object> artistPlaycount() {
		return getData("artist-playcount");
	}

	@Override
	public Map<String, Object> releaseListeners() {
		return getData("album-listeners");
	}

	@Override
	public Map<String, Object> releasePlaycount() {
		return getData("album-playcount");
	}

	@Override
	public Map<String, Object> recordingListeners() {
		return getData("track-listeners");
	}

	@Override
	public Map<String, Object> recordingPlaycount() {
		return getData("track-playcount");
	}

}
