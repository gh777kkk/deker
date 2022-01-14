package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductCart {

    private String mktProductId;
    private int productPrice;
    private int productQuantity;
    private String option1;
    private String option1Data;
    private String option2;
    private String option2Data;


}
