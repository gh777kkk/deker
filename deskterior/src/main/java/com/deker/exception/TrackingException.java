package com.deker.exception;

import lombok.Getter;

@Getter
public class TrackingException extends Exception {
    private String msg;
    public TrackingException(String msg){
        this.msg = msg;
    }
}
