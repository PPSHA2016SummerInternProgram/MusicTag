package com.paypal.musictag.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MusicTagUtil {

	public static Map<String, Object> createResultMap(boolean success, Object data, String errorMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("data", data);
		map.put("errorMessage", errorMessage);
		return map;
	}

	public static String getJsonFromURL(URL url) throws IOException {
		// Send request and get response
		
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.24.40.39", 3128));
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection(proxy);
		con.setRequestMethod("GET");

		System.out.println("connection opened");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		System.out.println(con.getContentType());
		System.out.println("response read");
		String line = null;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		String json = response.toString();
		return json;
	}

	/**
	 * Convert response from JSON string to Map
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	public static Map<String, Object> jsontoMap(String json)
			throws IOException, JsonParseException, JsonMappingException {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		Map<String, Object> map = mapper.readValue(json, typeRef);
		return map;
	}
}
