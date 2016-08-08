package com.paypal.musictag.dao.usingdb.resulthandler;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import com.paypal.musictag.util.MusicTagUtil;

public class ArtistCreditCountResultHandler implements ResultHandler<Map<String, Object>> {
	Map<String, Object> map = new HashMap<>();

	public Map<String, Object> getArtistCreditCountMap() {
		return map;
	}

	@Override
	public void handleResult(ResultContext<? extends Map<String, Object>> resultContext) {
		Map<String, Object> m = resultContext.getResultObject();
		map.put(MusicTagUtil.getFromMap(m, "name").toString(), m);
	}
}
