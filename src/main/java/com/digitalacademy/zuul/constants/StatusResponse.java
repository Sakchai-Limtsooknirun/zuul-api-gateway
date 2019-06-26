package com.digitalacademy.zuul.constants;

import lombok.Getter;

@Getter
public enum StatusResponse {

    // You should be able to create an enum object of a status response here.
    GET_SUCCESS_RESPONSE("1000", "success"),
    GET_TECHNICAL_ERROR_EXCEPTION("1899", "technical error"),
    GET_NOT_FOUND_ERROR_EXCEPTION("1999", "not found");

    private final String code;
    private final String message;

    StatusResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
