package com.paypal.musictag.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.exception.ResourceNotFoundException;

public final class MusicTagUtil {

	private MusicTagUtil() {
		// Just Empty
	}

	static final Properties properties;
	static {
		Resource resource = null;
		Properties props = null;
		resource = new ClassPathResource("musictag.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			throw new ResourceNotFoundException("can't load musictag.properties", e);
		}
		properties = props;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static Map<String, Object> wrapResult(Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("data", data);
		map.put("success", true);
		return map;
	}

	public static String encodeURIComponent(String s) {
		String result;
		try {
			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~")
					.replaceAll("\\%3D", "=").replaceAll("\\%26", "&").replaceAll("\\%2B", "+").replaceAll("\\%3A", ":")
					.replaceAll("\\%2F", "/").replaceAll("\\%3F", "?");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	private static String getJsonFromURL(URL url, boolean useProxy)
			throws NetConnectionException, ProtocolException, NetContentNotFoundException, NetBadRequestException {
		HttpURLConnection con = null;
		if (useProxy) {
			String proxyIp = properties.getProperty("proxyIp");
			Integer proxyPort = Integer.parseInt(properties.getProperty("proxyPort"));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
			try {
				con = (HttpURLConnection) url.openConnection(proxy);
			} catch (IOException e) {
				throw new NetConnectionException("url: " + url.toString() + " using proxy.", e);
			}
		} else {
			try {
				con = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				throw new NetConnectionException("url: " + url.toString(), e);
			}
		}

		// con.setRequestProperty("q", "+entity_type:release +\"周杰伦\"");

		con.setRequestMethod("GET");

		int code = -1;
		try {
			code = con.getResponseCode();
		} catch (IOException e1) {
			throw new NetConnectionException("url: " + url, e1);
		}
		if (code == HttpStatus.NOT_FOUND.value()) {
			throw new NetContentNotFoundException("url: " + url);
		} else if (code != HttpStatus.OK.value()) {
			throw new NetBadRequestException("url: " + url);
		}

		String response;
		InputStream stream = null;
		try {
			stream = con.getInputStream();
			response = IOUtils.toString(stream, "UTF-8");
		} catch (IOException e) {
			throw new NetContentNotFoundException("url: " + url.toString(), e);
		} finally {
			IOUtils.closeQuietly(stream);
		}

		return response;
	}

	public static String getJsonFromURLWithoutProxy(URL url)
			throws NetConnectionException, NetContentNotFoundException, NetBadRequestException, ProtocolException {
		return getJsonFromURL(url, false);
	}

	public static String getJsonFromURL(URL url)
			throws NetConnectionException, ProtocolException, NetContentNotFoundException, NetBadRequestException {

		return getJsonFromURL(url, "true".equals(properties.get("useProxy")));
	}

	/**
	 * Convert response from JSON string to Map
	 * 
	 * @param json
	 * @return
	 * @throws JsonMappingException
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, Object> jsontoMap(String json) throws JsonMappingException {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		Map<String, Object> map = null;
		try {
			map = mapper.readValue(json, typeRef);
		} catch (IOException e) {
			throw new JsonMappingException(json, e);
		}
		return map;
	}
}
