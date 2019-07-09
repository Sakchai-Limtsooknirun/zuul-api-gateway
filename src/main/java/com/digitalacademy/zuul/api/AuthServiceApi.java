package com.digitalacademy.zuul.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthServiceApi {
    private RestTemplate restTemplate;

    @Value("${spring.authService.host}")
    private String host;

    @Value("${spring.authService.endpoint.getVerify}")
    private String getVerify;

    @Autowired
    public AuthServiceApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String verifyUser(String token) throws Exception{
//        System.out.println("test auth");
        String url = host + "/" + getVerify;
        String data;

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JSONObject resp = new JSONObject(response.getBody());
        return resp.toString();

    }
}
