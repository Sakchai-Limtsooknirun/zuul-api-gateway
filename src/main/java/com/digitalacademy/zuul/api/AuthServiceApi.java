package com.digitalacademy.zuul.api;

import com.digitalacademy.zuul.model.GetAuthResponse;
import com.digitalacademy.zuul.utils.JsonToObjectConverter;
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
        JSONObject status = new JSONObject(resp.getString("status"));

        if(response.getStatusCode().value() == 200){
//            System.out.println("auth pass");
            data = resp.get("data").toString();
            return data;
        }
        System.out.println("error auth");
        throw new Exception();
    }
}
