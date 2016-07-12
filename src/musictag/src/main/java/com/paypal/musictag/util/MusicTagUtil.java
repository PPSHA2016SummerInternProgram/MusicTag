package com.paypal.musictag.util;

import java.util.HashMap;
import java.util.Map;

public final class MusicTagUtil {

	public static Map<String, Object> createResultMap(boolean success,
			Object data, String errorMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("data", data);
		map.put("errorMessage", errorMessage);
		return map;
	}
}
