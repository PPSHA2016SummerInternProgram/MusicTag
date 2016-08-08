package com.paypal.musictag.service.impl;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.dao.usingdb.ArtistRelationMapper;
import com.paypal.musictag.dao.usingdb.resulthandler.ArtistCreditCountResultHandler;
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
		ArtistCreditCountResultHandler handler = new ArtistCreditCountResultHandler();
		artistRelationMapper.findArtistCredit(UUID.fromString(artistGid), handler);
		return handler.getArtistCreditCountMap();
	}
}
