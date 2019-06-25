package com.digitalacademy.zuul.exceptions;

import com.digitalacademy.zuul.constants.StatusResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ZuulServiceException extends Exception {

    private HttpStatus httpStatus;
    private StatusResponse statusResponse;

    public ZuulServiceException(StatusResponse statusResponse, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.statusResponse = statusResponse;
    }
}
