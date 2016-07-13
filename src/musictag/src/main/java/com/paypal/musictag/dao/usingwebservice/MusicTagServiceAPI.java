package com.paypal.musictag.dao.usingwebservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class uses the music-brainz HTTP API to get data.
 * If cannot get data, throw an exception.
 * 
 * @author delliu
 */
public final class MusicTagServiceAPI {

    final static String URL = "http://10.24.53.72:5000/ws/2/";

    public static Map<String, Object> sendRequest(String subUrl,
            Map<String, String> params) throws IOException {

        // Build URL
        StringBuilder url = new StringBuilder(URL).append(subUrl).append("?");
        StringBuilder paramsStr = new StringBuilder("fmt=json&");
        if (params != null) {
            for (Entry<String, String> pair : params.entrySet()) {
                paramsStr.append(pair.getKey()).append("=")
                        .append(pair.getValue()).append("&");
            }
        }
        String u = url.append(paramsStr).toString();
        URL obj = new URL(u);
        System.out.println("send request: " + u);

        // Send request and get response
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String line = null;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        String json = response.toString();
        System.out.println(json);

        // Convert response from JSON string to Map
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> map = mapper.readValue(json, typeRef);

        return map;
    }
}
