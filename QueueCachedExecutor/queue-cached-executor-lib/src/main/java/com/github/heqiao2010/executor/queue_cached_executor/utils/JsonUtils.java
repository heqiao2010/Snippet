package com.github.heqiao2010.executor.queue_cached_executor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        if (value == null) {
            return null;
        }
        return mapper.writeValueAsString(value);
    }

    public static <T> T readValue(String input, Class<T> clz) {
        if (input == null) {
            return null;
        }

        try {
            return mapper.readValue(input, clz);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}
