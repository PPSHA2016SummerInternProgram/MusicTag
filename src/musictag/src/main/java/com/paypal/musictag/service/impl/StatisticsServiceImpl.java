package com.paypal.musictag.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.dao.usingdb.ArtistRelationMapper;
import com.paypal.musictag.service.StatisticsService;
import com.paypal.musictag.util.MusicTagUtil;

@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private HotStatisticsDao hotStatisticsDaoImpl;
	@Autowired 
	private ArtistRelationMapper artistRelationMapper;

	@Override
	public Map<String, Object> artistListeners(String gid) {
		return hotStatisticsDaoImpl.artistListeners(gid);

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
		//link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper.findArtistCreditLinkWithCount(UUID.fromString(artistGid));
		
		return constructResult(linksWithCount);
	}
	
	private void postProcessLinkAndNode(List<Map<String, Object>> nodes,
			List<Map<String, Object>> linksWithCount){
		moveCountFromLinktoNodes(nodes, linksWithCount);
		
	}
	
	private void moveCountFromLinktoNodes(List<Map<String, Object>> nodes,
			List<Map<String, Object>> linksWithCount) {
		for(Map<String, Object> link : linksWithCount){
			for(Map<String, Object> node : nodes){
				if(link.get("target").equals(node.get("id"))){
					node.put("symbolSize", ((Long)link.get("count") + (Long)(node.get("symbolSize") == null ? 0l : node.get("symbolSize"))));
				}
			}
			link.remove("count");
		}
	}

	@Override
	public Map<String, Object> artistLyricist(String artistGid) {
		//link info
		List<Map<String, Object>> linksWithCount = artistRelationMapper.findReleaseLyricistLink(UUID.fromString(artistGid));
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
		List<Map<String, Object>> linksWithCount = artistRelationMapper.findReleaseComposerLink(UUID.fromString(artistGid));
		linksWithCount.addAll(artistRelationMapper.findRecordingComposerLink(UUID.fromString(artistGid)));
		
		return constructResult(linksWithCount);
	}
}
