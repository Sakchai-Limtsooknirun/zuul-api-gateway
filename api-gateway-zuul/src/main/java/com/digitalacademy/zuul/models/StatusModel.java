package com.digitalacademy.zuul.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StatusModel implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public StatusModel() {
    }

    public StatusModel(String code, String message) {
        this.code = code;
        this.message = message;
    }
}