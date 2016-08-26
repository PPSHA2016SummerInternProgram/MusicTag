package com.paypal.musictag.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.dao.mongo.mapper.Distribution;
import com.paypal.musictag.dao.mongo.mapper.LastfmArtist;
import com.paypal.musictag.dao.usingdb.ArtistRelationMapper;
import com.paypal.musictag.dao.usingdb.DistributionMapper;
import com.paypal.musictag.service.StatisticsService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private HotStatisticsDao hotStatisticsDaoImpl;
	@Autowired
	private ArtistRelationMapper artistRelationMapper;
	@Autowired
	private DistributionMapper distributionMapper;

	@Resource(name = "mongoTemplate")
	private MongoTemplate mongoTemplate;

	final private static String SCORES_CACHE_COLLECTION = "scores.cache";

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> distributionScores(String gid) {

		Query searchQuery = new Query(Criteria.where("gid").is(gid));
		Map<String, Object> cache = mongoTemplate.findOne(searchQuery, Map.class, SCORES_CACHE_COLLECTION);
		if(cache != null){
			return (Map<String, Object>) cache.get("data");
		}
		
		String[] types = { "edit_amount", "recording_amount", "release_amount", "active_years", "contacts_amount",
				"country_amount", "listener_amount", "play_amount" };
		Map<String, Object> result = new HashMap<>();
		for (String type : types) {
			Map<String, Object> item = distributionDetail(gid, type);
			item.put("distribution", null);
			result.put(type, item);
		}
		
		cache = new HashMap<>();
		cache.put("gid", gid);
		cache.put("data", result);
		mongoTemplate.save(cache, SCORES_CACHE_COLLECTION);
		
		return result;
	}

	@Override
	public Map<String, Object> distributionDetail(String gid, String type) {
		checkDistributionType(type);

		Query searchQuery = new Query(Criteria.where("type").is(type));
		Distribution distribution = mongoTemplate.findOne(searchQuery, Distribution.class);

		Map<String, Object> map = new HashMap<>();
		map.put("distribution", distribution);
		map.put("rank", findRank(gid, type, distribution.getMin(), distribution.getMax()));

		return map;
	}

	private Map<String, Object> findRank(String gid, String type, int min, int max) {
		int amount = 0;
		int xAxis = 0;
		int prettyXAxis = 0;
		int score = 0;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("gid", UUID.fromString(gid));
			switch (type) {
			case "edit_amount":
				amount = distributionMapper.editAmount(params);
				xAxis = (int) (Math.log(1 + Math.pow(amount, 0.2)) * 15);
				break;
			case "recording_amount":
				amount = distributionMapper.recordingAmount(params);
				xAxis = (int) (Math.log((1 + Math.pow(amount, 0.7))) * 5);
				break;
			case "release_amount":
				amount = distributionMapper.releaseAmount(params);
				xAxis = (int) (Math.log((1 + Math.pow(amount, 0.6))) * 5);
				break;
			case "active_years":
				amount = distributionMapper.activeYears(params);
				xAxis = (int) (((Math.pow(amount, 0.2) - 0.7)) * 10);
				break;
			case "contacts_amount":
				amount = distributionMapper.contactsAmount(params);
				xAxis = (int) (((Math.pow(amount, 0.02) - 0.95)) * 100);
				break;
			case "country_amount":
				amount = distributionMapper.countryAmount(params);
				xAxis = (int) (Math.pow(amount, 0.48) * 5);
				break;
			case "listener_amount":
				amount = getArtistListenersOrPlay(gid, "listeners");
				xAxis = (int) (Math.log(amount + 1) * 1.5);
				break;
			case "play_amount":
				amount = getArtistListenersOrPlay(gid, "playcount");
				xAxis = (int) (Math.log(amount + 1) * 1.5);
				break;
			default:
			}
			prettyXAxis = Math.min(Math.max(min, xAxis), max);
			score = (int) ((double) (prettyXAxis - min) / (max - min) * 100);
		} catch (Exception e) {
			// Ignore the exception.
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("amount", amount);
		map.put("xAxis", xAxis);
		map.put("score", score);
		return map;
	}

	private int getArtistListenersOrPlay(String gid, String type) {
		Query searchQuery = new Query(Criteria.where("gid").is(gid));
		LastfmArtist artist = mongoTemplate.findOne(searchQuery, LastfmArtist.class);
		int amount = 0;
		if (artist != null) {
			Map<String, Object> stats = artist.getStats();
			if (stats != null) {
				amount = Integer.parseInt(String.valueOf(stats.get(type)));
			}
		}
		return amount;
	}

	private void checkDistributionType(String type) {
		String[] types = { "edit_amount", "recording_amount", "release_amount", "active_years", "contacts_amount",
				"country_amount", "listener_amount", "play_amount" };
		for (String t : types) {
			if (t.equals(type)) {
				return;
			}
		}
		throw new IllegalArgumentException("Don't support distribution type: " + type);
	}

	@Override
	public Map<String, Object> distribution() {
		List<Distribution> list = mongoTemplate.findAll(Distribution.class);
		Map<String, Object> map = new HashMap<>();
		map.put("distribution", list);
		return map;
	}

	@Override
	public Map<String, Object> artistListeners(String gid) {
		return hotStatisticsDaoImpl.artistListeners(gid);

	}

	@Override
	public Map<String, Object> releaseInfo(String gid) {
		return hotStatisticsDaoImpl.getReleaseInfo(gid);

	}
	
	@Override
	public Map<String, Object> artistPlaycount(String gid) {
		return hotStatisticsDaoImpl.artistPlaycount(gid);
	}

	@Override
	public Map<String, Object> releaseListeners(String gid) {
		return hotStatisticsDaoImpl.releaseListeners(gid);
	}

	@Override
	public Map<String, Object> releasePlaycount(String gid) {
		return hotStatisticsDaoImpl.releasePlaycount(gid);
	}

	@Override
	public Map<String, Object> recordingListeners(String gid) {
		return hotStatisticsDaoImpl.recordingListeners(gid);
	}

	@Override
	public Map<String, Object> recordingPlaycount(String gid) {
		return hotStatisticsDaoImpl.recordingPlaycount(gid);
	}

	@Override
	public Map<String, Object> artistCreditCount(String artistGid) {

		// link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper
				.findArtistCreditLinkWithCount(UUID.fromString(artistGid));
		// node info
		List<Integer> ids = MusicTagUtil.extractUniqueIds(linksWithCount);
		List<Map<String, Object>> nodes = new LinkedList<>();
		if (ids.size() > 0) {
			nodes = artistRelationMapper.findArtistCreditNode(ids);
			postProcessLinkAndNode(nodes, linksWithCount);
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("links", linksWithCount);
		resultMap.put("nodes", nodes);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> artistReleaseYearlyDist(String artistGid) {
		return artistRelationMapper.getReleaseYearlyDist(UUID.fromString(artistGid));
	}

	private void postProcessLinkAndNode(List<Map<String, Object>> nodes, List<Map<String, Object>> linksWithCount) {
		moveCountFromLinktoNodes(nodes, linksWithCount);

	}

	private void moveCountFromLinktoNodes(List<Map<String, Object>> nodes, List<Map<String, Object>> linksWithCount) {
		for (Map<String, Object> link : linksWithCount) {
			for (Map<String, Object> node : nodes) {
				if (link.get("target").equals(node.get("id"))) {
					node.put("symbolSize", ((Long) link.get("count")
							+ (Long) (node.get("symbolSize") == null ? 0l : node.get("symbolSize"))));
				}
			}
			link.remove("count");
		}
	}

	public List<Map<String, Object>> artistAreaCount(String artistGid) {
		List<Map<String, Object>> mapList = artistRelationMapper.getArtistAreaCount(UUID.fromString(artistGid));
		
		return mapList;
	}
	
	public List<Map<String, Object>> artistAreaDetails(String artistGid,String area) {
		int length = 0;
		List<Map<String, Object>> mapList = artistRelationMapper.getArtistAreaDetails(UUID.fromString(artistGid));
		List<Map<String,Object>> areaRelease = new ArrayList<Map<String,Object>>();
		if(area.equals("China")||area.equals("Taiwan")||area.equals("Hong Kong")){
			for(Map<String,Object> release: mapList){
				if (release.get("area").equals("China") || release.get("area").equals("Taiwan")
						|| release.get("area").equals("Hong Kong")) {
					areaRelease.add(release);
					length++;
					if(length>4){
						break;
					}
				}
			}	
		}else{
			for(Map<String,Object> release: mapList){
				if(release.get("area").equals(area)) {
					areaRelease.add(release);
					length++;
					if(length>4){
						break;
					}
				}
			}
		}
		return areaRelease;
	}
	
	public List<Map<String, Object>> artistEdit(String artistGid){
		List<Map<String, Object>> tryList = artistRelationMapper.getArtistEdit(UUID.fromString(artistGid));

		return tryList;
	}
	
	@Override
	public Map<String, Object> artistLyricist(String artistGid) {
		//link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper
				.findReleaseLyricistLink(UUID.fromString(artistGid));
		linksWithCount.addAll(artistRelationMapper.findRecordingLyricistLink(UUID.fromString(artistGid)));
		
		return constructResult(linksWithCount);
	}

	private Map<String, Object> constructResult(List<Map<String, Object>> linksWithCount) {
		List<Integer> ids = MusicTagUtil.extractUniqueIds(linksWithCount);
		List<Map<String, Object>> nodes = new LinkedList<>();
		if (ids.size() > 0) {
			nodes = artistRelationMapper.findArtistNode(ids);	
			postProcessLinkAndNode(nodes, linksWithCount);
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("links", linksWithCount);
		resultMap.put("nodes", nodes);
		return resultMap;
	}

	@Override
	public Map<String, Object> artistComposer(String artistGid) {
		//link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper
				.findReleaseComposerLink(UUID.fromString(artistGid));
		linksWithCount.addAll(artistRelationMapper.findRecordingComposerLink(UUID.fromString(artistGid)));
		
		return constructResult(linksWithCount);
	}
}
