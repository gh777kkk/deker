package com.deker.cmm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alarm {
    private String alarmId;
    private String sentInfo;
    private String alarmCode;
    private String createDt;
    private String url;
    private String message;
    private String alarmFromNm;
    private String alarmFromImg;
    private String alarmDelay;
}
