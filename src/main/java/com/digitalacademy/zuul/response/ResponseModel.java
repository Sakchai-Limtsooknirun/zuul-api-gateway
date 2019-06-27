package com.digitalacademy.zuul.response;

import com.digitalacademy.zuul.constants.StatusResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Data
public class ResponseModel implements Serializable {

    private String code;
    private String message;

    @Override
    public String toString() {
        JSONObject jsonObj = new JSONObject();
        try {
            JSONObject data = new JSONObject();
//            data.put("data1","test1");
//            data.put("data2","test2");


            jsonObj.put("code", code);
            jsonObj.put("message", message);
//            jsonObj.put("data", data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();

    }


}
