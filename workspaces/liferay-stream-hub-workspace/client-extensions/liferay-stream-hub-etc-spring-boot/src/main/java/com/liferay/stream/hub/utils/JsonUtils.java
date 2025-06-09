package com.liferay.stream.hub.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
