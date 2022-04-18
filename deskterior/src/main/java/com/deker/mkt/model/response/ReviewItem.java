package com.deker.mkt.model.response;

import com.deker.mkt.model.ProductOption;
import com.deker.mkt.model.resultService.MarketAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ReviewItem {

    private String reviewId;
    private String memId;
    private String mktProductId;
    private String productName;
    private int productTotalPrice;
    private int deliveryPay;
    private String productImg;
    private String option1;
    private String option1Name;
    private String option1Data;
    private String option1DataName;
    private String option2;
    private String option2Name;
    private String option2Data;
    private String option2DataName;

}
