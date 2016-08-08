package com.paypal.musictag.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.HotStatisticsDao;
import com.paypal.musictag.service.StatisticsService;

@Service("statisticsServiceImpl")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private HotStatisticsDao hotStatisticsDaoImpl;

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
}
