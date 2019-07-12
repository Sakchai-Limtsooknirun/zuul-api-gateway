package com.digitalacademy.zuul.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusResponse {

    GET_INTERNAL_SERVER_ERROR_EXCEPTION(9900, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    GET_TOO_MANY_REQUEST(9901, HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase()),
    GET_HEADER_TOKEN_NOT_FOUND(9902, "no access token in header"),
    GET_EXPIRED_ERROR_EXCEPTION(1956, "expired error"),
    GET_NOT_FOUND_ERROR_EXCEPTION(1699, "not found");

    private final int code;
    private final String message;

    StatusResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
