package com.digitalacademy.zuul.constants;

import lombok.Getter;

@Getter
public enum StatusResponse {

    GET_INTERNAL_SERVER_ERROR_EXCEPTION("9900", "Unsupported content type defined");

    private final String code;
    private final String message;

    StatusResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
