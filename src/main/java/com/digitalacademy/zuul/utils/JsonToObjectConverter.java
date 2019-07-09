package com.digitalacademy.zuul.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;

public class JsonToObjectConverter {
    private static ObjectMapper obMap = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    public static <T> T readValue (String jsonValue, Class<T> tClass) throws IOException {
        return obMap.readValue(jsonValue, tClass);
    }
}
