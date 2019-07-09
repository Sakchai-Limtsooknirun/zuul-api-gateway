package com.digitalacademy.zuul.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAuthResponse {
    private Long user_id;
}
