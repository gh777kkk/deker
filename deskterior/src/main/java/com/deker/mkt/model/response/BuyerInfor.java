package com.deker.mkt.model.response;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class BuyerInfor {

    private String code;
    private String message;
    private JSONObject response = new JSONObject();
}
