package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TrackingData {
    private String code;
    private String msg;
    private String result;
    private String itemName;
    private String invoiceNo;
    private String estimate;
    private Integer level;
    private String levelNm;
    private Long time;
    private String timeString;
    private String where;
    private String kind;
    private String telno;
    private List<TrackingData> trackingDetails;
    private List<TrackingData> body;

    public String getTimeFormat() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateString = new SimpleDateFormat("yyyyMMddHHmmss");
        Date trackingDate = new Date(dateFormat.parse(timeString).getTime());
        timeString = dateFormat.format(trackingDate);
        return timeString;
    }
}
