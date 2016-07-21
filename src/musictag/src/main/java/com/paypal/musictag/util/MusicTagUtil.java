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
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MusicTagUtil {

    private static final Logger logger = Logger.getLogger(MusicTagUtil.class);
    
    private MusicTagUtil() {
        // Just Empty
    }

	static final Properties properties;
	static {
		Resource resource = null;
		Properties props = null;
		try {
			resource = new ClassPathResource("musictag.properties");
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.error(e);
		}
		properties = props;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static Map<String, Object> createResultMap(boolean success,
			Object data, ResponseCode code) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", success);
		map.put("data", data);
		if (code != null) {
			map.put("errorMessage", code.getMsg());
			map.put("responseCode", code.getValue());
		}else {
			map.put("errorMessage", ResponseCode.NOT_PROVIDED.getMsg());
			map.put("responseCode", ResponseCode.NOT_PROVIDED.getValue());
		}
		return map;
	}
	
	public static Map<String, Object> createResultMap(boolean success,
			Object data, String errorMessage, ResponseCode code) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", success);
		map.put("data", data);
		map.put("errorMessage", errorMessage == null ? ResponseCode.NOT_PROVIDED.getMsg() : errorMessage);
		map.put("responseCode", code == null ? ResponseCode.NOT_PROVIDED : code.getValue());
		return map;
	}

	public static String getJsonFromURL(URL url) throws IOException {
		// Send request and get response

		HttpURLConnection con;
		if ("true".equals(properties.get("useProxy"))) {
			String proxyIp = properties.getProperty("proxyIp");
			Integer proxyPort = Integer.parseInt(properties
					.getProperty("proxyPort"));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyIp, proxyPort));
			con = (HttpURLConnection) url.openConnection(proxy);
		} else {
			con = (HttpURLConnection) url.openConnection();
		}
		con.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		return response.toString();
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
			throws IOException, JsonMappingException {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		Map<String, Object> map = mapper.readValue(json, typeRef);
		return map;
	}
}
