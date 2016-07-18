package com.paypal.musictag.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ReleaseesCountsMapResultHandler implements ResultHandler<Map<String, Object>> {
	Map<String, Long> map = new HashMap<>();

	public Map<String, Long> getReleaseCountMap(){
		return map;
	}
	@Override
	public void handleResult(ResultContext<? extends Map<String, Object>> rc) {
		Map<String, Object> m = rc.getResultObject();
		map.put(getFromMap(m, "gid").toString(), (Long)getFromMap(m, "count"));
	}

	private Object getFromMap(Map<String, Object> map, String key) {
		if (map.containsKey(key.toLowerCase())) {
			return map.get(key.toLowerCase());
		} else {
			return map.get(key.toUpperCase());
		}
	}
}
