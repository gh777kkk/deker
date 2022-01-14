package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductCart {

    private String mktCartId;
    private String memId;
    private String mktProductId;
    private int Price;
    private int cartQuant;
    private String option1;
    private String option1Data;
    private String option2;
    private String option2Data;


}
