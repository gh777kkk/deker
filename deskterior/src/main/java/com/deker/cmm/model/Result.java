package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {
    private String responseCode;
    private String message;
    private List<?> data;

    public Result(String responseCode){
        this.responseCode = responseCode;
    }

    public Result(String responseCode, String message){
        this.message = message;
        this.responseCode = responseCode;
    }

    public Result(String responseCode, String message, List<?> data){
        this.data = data;
        this.message = message;
        this.responseCode = responseCode;
    }
}
