package com.digitalacademy.zuul.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusModel implements Serializable {

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public StatusModel() {
    }

    public StatusModel(int code, String message) {
        this.code = code;
        this.message = message;
    }
}