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
    private String productOptionId;
    private int orderQuantity;
    private String addId;


}
