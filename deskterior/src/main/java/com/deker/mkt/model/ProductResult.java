package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResult {
    private String responseCode;
    private String message;
    private List<?> bestProduct;
    private List<?> newProduct;
    private List<?> productOption;
    private List<?> productExplain;
    private List<?> recommendedProduct;
    private List<?> productReview;

    public ProductResult(String responseCode){
        this.responseCode = responseCode;
    }

    public ProductResult(String responseCode, String message){
        this.message = message;
        this.responseCode = responseCode;
    }

    public ProductResult(String responseCode, String message, List<?> data1){
        this.bestProduct = data1;
        this.message = message;
        this.responseCode = responseCode;
    }

    public ProductResult(String responseCode, String message, List<?> data1, List<?> data2){
        this.bestProduct = data1;
        this.newProduct = data2;
        this.message = message;
        this.responseCode = responseCode;
    }

    public ProductResult(String responseCode, String message, List<?> data1, List<?> data2,
                         List<?> data3, List<?> data4){
        this.productOption = data1;
        this.productExplain = data2;
        this.recommendedProduct = data3;
        this.productReview = data4;
        this.message = message;
        this.responseCode = responseCode;
    }


}
