package com.deker.mkt.model.request;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class CancleBuy {

    private String code;
    private String message;
    private JSONObject response = new JSONObject();
}
