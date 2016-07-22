package com.paypal.musictag.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.musictag.controller.ArtistController;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public final class MusicTagUtil {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);
    
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
			logger.error(null, e);
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

	public static String getJsonFromURL(URL url) throws NetConnectionException, ProtocolException, NetContentNotFoundException{
		// Send request and get response
		HttpURLConnection con = null;
		if ("true".equals(properties.get("useProxy"))) {
			String proxyIp = properties.getProperty("proxyIp");
			Integer proxyPort = Integer.parseInt(properties
					.getProperty("proxyPort"));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					proxyIp, proxyPort));
			try {
				con = (HttpURLConnection) url.openConnection(proxy);
			} catch (IOException e) {
				logger.error("Connect to {} using proxy. Cause: {}",url.toString(), e);
				throw new NetConnectionException();
			}
		} else {
			try {
				con = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				logger.error("connect to {}. Cause: {}", url.toString(), e);
				throw new NetConnectionException();
			}
		}
		
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			logger.error("set request method to Get. {}", e);
			throw e;
		}

		String response;
		InputStream stream = null;
		try {
			stream = con.getInputStream();
			response = IOUtils.toString(stream, "UTF-8");
		} catch (IOException e) {
			logger.error("read from {}. Cause: {}", url.toString(), e);
			throw new NetContentNotFoundException();
		}finally {
			 IOUtils.closeQuietly(stream);
		}
		
		return response;
	}

	/**
	 * Convert response from JSON string to Map
	 * 
	 * @param json
	 * @return
	 * @throws JsonMappingException
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, Object> jsontoMap(String json) throws JsonMappingException
			 {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, typeRef);
		} catch (IOException e) {
			logger.error("Json to map error. Json: {} Cause: {}", json, e);
			throw new JsonMappingException(json);
		}
		return map;
	}
}
