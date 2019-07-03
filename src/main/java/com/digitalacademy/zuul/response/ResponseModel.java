package com.digitalacademy.zuul.response;

import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Data
public class ResponseModel implements Serializable {

    private int code;
    private String message;

    @Override
    public String toString() {

        JSONObject data = new JSONObject();
        try {
            data.put("code", code);
            data.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject status = new JSONObject();
        try {
            status.put("status", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return status.toString();

    }


}
