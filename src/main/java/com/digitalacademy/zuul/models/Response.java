package com.digitalacademy.zuul.models;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    @JsonProperty("status")
    private StatusModel status;

    @JsonProperty("data")
    private GetAuthResponse data;

    public Response() {
    }

    public Response(StatusModel statusModel) {
        this.status = statusModel;
    }

    public Response(StatusModel statusModel, GetAuthResponse data) {
        this.status = statusModel;
        this.data = data;
    }


}
