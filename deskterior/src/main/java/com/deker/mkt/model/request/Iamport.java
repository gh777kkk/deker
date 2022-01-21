package com.deker.mkt.model.request;


import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class Iamport {
    private String code;
    private String message;
    private JSONObject response = new JSONObject();
}
