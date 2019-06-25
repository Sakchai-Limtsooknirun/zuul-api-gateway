package com.digitalacademy.zuul.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;

public class JsonToObjectConverter {
    // This class should appear in ~.api package.
    // This class will be used when you send an API request to and get a response from other microservice.
    // The method provided below will read the JSON's data content and try to map it with a selected Object,
    // which also explains why `GetLoanInfoResponse` in `customerservice` has exactly the same attributes as-
    // `LoanStatusResponse` in `loanservice`.
    // Further references are provided in `customerservice` and `loanservice` project.
    /**
     * Mapping data pattern from snake_case (JSON format) into Java model object.
     */
    private static ObjectMapper objectMapper =
            new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    /**
     * This method will read the jsonValue and try to map it with a tClass Object.
     * @param jsonValue Data content in json format, which is snake case
     * @param tClass    Model class
     * @param <T>       Model type
     * @return Model object
     * @throws IOException Invalid data body
     */
    public static <T> T readValue(String jsonValue, Class<T> tClass) throws IOException {
        return objectMapper.readValue(jsonValue, tClass);
    }
}
