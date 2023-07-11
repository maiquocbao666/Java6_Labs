package com.rest;

import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Rest {

    private static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode get(String path) {
        return request("GET", path, null);
    }

    public static void delete(String path, Object data) {
        request("DELETE", path, data);
    }

    public static JsonNode put(String path, Object data) {
        return request("PUT", path, data);
    }

    public static JsonNode post(String path, Object data) {
        return request("POST", path, data);
    }

    private static JsonNode request(String method, String path, Object data) {
        try {
            // 1. REQUEST
            String uri = "https://poly-java6-67f5a-default-rtdb.firebaseio.com" + path + ".json";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application.json");
            connection.setRequestMethod(method);

            // 1.1 DATA (POST & PUT only)
            if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("PUT")) {
                connection.setDoOutput(true);
                mapper.writeValue(connection.getOutputStream(), data);
            }

            // 2. RESPONSE
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return mapper.readTree(connection.getInputStream());
            }
            connection.disconnect();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
