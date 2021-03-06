package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductModel {

    private String mktProductId;
    private int productPrice;
    private int productQuantity;
    private String productCategory;
    private String productExplain;
    private String productName;
    private String productImg;
    private String productBrand;


}
