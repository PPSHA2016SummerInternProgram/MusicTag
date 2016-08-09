package com.paypal.musictag.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.dao.usingdb.ArtistRelationMapper;
import com.paypal.musictag.service.StatisticsService;

@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private HotStatisticsDao hotStatisticsDaoImpl;
	@Autowired 
	private ArtistRelationMapper artistRelationMapper;

	@Override
	public Map<String, Object> artistListeners() {
		return hotStatisticsDaoImpl.artistListeners();

	}

	@Override
	public Map<String, Object> artistPlaycount() {
		return hotStatisticsDaoImpl.artistPlaycount();
	}

	@Override
	public Map<String, Object> releaseListeners() {
		return hotStatisticsDaoImpl.releaseListeners();
	}

	@Override
	public Map<String, Object> releasePlaycount() {
		return hotStatisticsDaoImpl.releasePlaycount();
	}

	@Override
	public Map<String, Object> recordingListeners() {
		return hotStatisticsDaoImpl.recordingListeners();
	}

	@Override
	public Map<String, Object> recordingPlaycount() {
		return hotStatisticsDaoImpl.recordingPlaycount();
	}

	@Override
	public Map<String, Object> artistCreditCount(String artistGid) {
		//link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper.findArtistCreditLinkWithCount(UUID.fromString(artistGid));
		//node info
		List<Integer> ids = extractUniqueIds(linksWithCount);
		List<Map<String, Object>> nodes = artistRelationMapper.findArtistCreditNode(ids);
		addCounttoNodes(nodes, linksWithCount);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("links", linksWithCount);
		resultMap.put("nodes", nodes);
		return resultMap;
	}
	
	
	
	private void addCounttoNodes(List<Map<String, Object>> nodes,
			List<Map<String, Object>> linksWithCount) {
		for(Map<String, Object> link : linksWithCount){
			for(Map<String, Object> node : nodes){
				if(link.get("target").equals(node.get("id"))){
					node.put("symbolSize", ((Long)link.get("count") + (Long)(node.get("symbolSize") == null ? 0l : node.get("symbolSize"))));
				}
			}
		}
	}

	private List<Integer> extractUniqueIds(List<Map<String, Object>> links){
		Set<Integer> idSet = new HashSet<>();
		for(Map<String, Object> map:links){
			idSet.add((Integer) map.get("source"));
			idSet.add((Integer) map.get("target"));
		}
		List<Integer> res = new LinkedList<>();
		res.addAll(idSet);
		return res;
	}
}
