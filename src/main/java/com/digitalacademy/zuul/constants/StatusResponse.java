package com.digitalacademy.zuul.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusResponse {

    GET_INTERNAL_SERVER_ERROR_EXCEPTION(9900, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    private final int code;
    private final String message;

    StatusResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
