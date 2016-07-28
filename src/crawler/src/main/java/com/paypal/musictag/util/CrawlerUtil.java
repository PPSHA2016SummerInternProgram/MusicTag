package com.paypal.musictag.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrawlerUtil {

	public static String getJsonFromURL(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		String response;
		InputStream stream = null;
		try {
			stream = connection.getInputStream();
			response = IOUtils.toString(stream, "UTF-8");
		} finally {
			IOUtils.closeQuietly(stream);
		}
		return response;
	}

	public static Map<String, Object> jsontoMap(String json)
			throws JsonParseException, JsonMappingException, IOException {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		return mapper.readValue(json, typeRef);
	}
}
