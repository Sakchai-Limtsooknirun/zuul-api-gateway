package com.digitalacademy.zuul.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAuthResponse {
    private Long user_id;
}
