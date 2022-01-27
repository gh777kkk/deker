package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DeliveryStatus {
    private String waybill;
    private String deliveryCode;
    private String orderId;
}
