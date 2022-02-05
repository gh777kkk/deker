package com.deker.mkt.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class MyShoppingList {
    private String productId;
    private String orderId;
    private String productImg;
    private String productBrand;
    private String productName;
    private String option1;
    private String option1Data;
    private String option1Nm;
    private String option1DataNm;
    private String option2;
    private String option2Data;
    private String option2Nm;
    private String option2DataNm;
    private List<String> optionList;
    private String orderNumber;
    private Date createDt;
    private String stringDt;
    private String orderState;
    private String orderStateNm;
    private int orderPrice;
    private int orderQuantity;
}
