package com.digitalacademy.zuul.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    @JsonProperty("status")
    private StatusModel status;

    @JsonProperty("data")
    private Object data;

    public ResponseModel() { }

    public ResponseModel(StatusModel statusModel) {
        this.status = statusModel;
    }

    public ResponseModel(StatusModel statusModel, Object data) {
        this.status = statusModel;
        this.data = data;
    }

    public HttpEntity<ResponseModel> build(HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseModel(this.status, this.data), httpStatus);
    }
}
