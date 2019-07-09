package com.digitalacademy.zuul.models;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel implements Serializable {

    @JsonProperty("status")
    private StatusModel status;

    @JsonProperty("data")
    private GetAuthResponse data;

    public ResponseModel() {
    }

    public ResponseModel(StatusModel statusModel) {
        this.status = statusModel;
    }

    public ResponseModel(StatusModel statusModel, GetAuthResponse data) {
        this.status = statusModel;
        this.data = data;
    }


}
