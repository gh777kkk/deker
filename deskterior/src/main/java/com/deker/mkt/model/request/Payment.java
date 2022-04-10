package com.deker.mkt.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Payment {
    private String imp_uid;
    private String memId;
    private int paid_amount;
    private List<String> cartIdArr;
    private List<String> productOptionId;
    private List<Integer> orderQuantity;
    private String addId;
    private String optionId;
    private int quantity;
    private String orderId;
    private String itemOrderId;

}
