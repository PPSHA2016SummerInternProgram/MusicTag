package com.paypal.musictag.dao.usingdb.resulthandler;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import com.paypal.musictag.util.MusicTagUtil;

public class ReleaseesCountsMapResultHandler implements ResultHandler<Map<String, Object>> {
	Map<String, Long> map = new HashMap<>();

	public Map<String, Long> getReleaseCountMap(){
		return map;
	}
	@Override
	public void handleResult(ResultContext<? extends Map<String, Object>> rc) {
		Map<String, Object> m = rc.getResultObject();
		map.put(MusicTagUtil.getFromMap(m, "gid").toString(), (Long)MusicTagUtil.getFromMap(m, "count"));
	}

}
