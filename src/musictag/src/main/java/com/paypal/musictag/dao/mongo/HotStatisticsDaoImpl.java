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
import com.paypal.musictag.dao.mongo.mapper.LastfmAlbumOrTrack;
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
		fillRank(map, type, gid);
		return map;
	}

	
	@Override
	public Map<String, Object> releaseInfo(String gid) {
		return getReleaseInfo("release", "aadd2a0e-5702-412c-a509-76b89e4411f6");
	}

	private Map<String, Object> getReleaseInfo(String type, String gid) {
		Query searchQuery = new Query(Criteria.where("gid").is(gid));
		LastfmAlbumOrTrack album = mongoTemplate.findOne(searchQuery, LastfmAlbumOrTrack.class, "lastfm.album");
		int listeners=album.getListeners();
		int playcount=album.getPlaycount();
		Map<String,Object> map = new HashMap<>();
		//System.out.println("-------------------------");
		//System.out.println("----------------------"+listeners+playcount+"------------------------");
		map.put("listeners", listeners);
		map.put("playcount", playcount);
		//fillRank(map, type, gid);
		return map;
	}
	
	
	private void fillRank(Map<String, Object> map, String type, String gid) {

		String type0 = type.split("-")[0];
		String type1 = type.split("-")[1];

		if ("artist".equals(type0)) {
			Query searchQuery = new Query(Criteria.where("gid").is(gid));
			LastfmArtist artist = mongoTemplate.findOne(searchQuery, LastfmArtist.class);
			if (artist != null) {
				Map<String, Object> stats = artist.getStats();
				if (stats != null) {
					int amount = Integer.parseInt(String.valueOf(stats.get(type1)));
					map.put("amount", amount);
					map.put("rank", getHotRank(type, amount));
					Long totalAmount = mongoTemplate.count(null, Long.class, "lastfm." + type.split("-")[0]);
					map.put("total", totalAmount.intValue());
				}
			}
		} else {
			Query searchQuery = new Query(Criteria.where("gid").is(gid));
			int amount = 0;
			if ("album".equals(type0)) {
				LastfmAlbumOrTrack album = mongoTemplate.findOne(searchQuery, LastfmAlbumOrTrack.class, "lastfm.album");
				amount = Integer.parseInt(
						String.valueOf("listeners".equals(type1) ? album.getListeners() : album.getPlaycount()));
			} else {
				LastfmAlbumOrTrack track = mongoTemplate.findOne(searchQuery, LastfmAlbumOrTrack.class, "lastfm.track");
				amount = Integer.parseInt(
						String.valueOf("listeners".equals(type1) ? track.getListeners() : track.getPlaycount()));
			}
			map.put("amount", amount);
			map.put("rank", getHotRank(type, amount));
			Long totalAmount = mongoTemplate.count(null, Long.class, "lastfm." + type0);
			map.put("total", totalAmount.intValue());
		}

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
	public Map<String, Object> artistPlaycount(String gid) {
		return getData("artist-playcount", gid);
	}

	@Override
	public Map<String, Object> releaseListeners(String gid) {
		return getData("album-listeners", gid);
	}

	@Override
	public Map<String, Object> releasePlaycount(String gid) {
		return getData("album-playcount", gid);
	}

	@Override
	public Map<String, Object> recordingListeners(String gid) {
		return getData("track-listeners", gid);
	}

	@Override
	public Map<String, Object> recordingPlaycount(String gid) {
		return getData("track-playcount", gid);
	}
	
	
	
}
