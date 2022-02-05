package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductKeyword {

    private String keyword;
    private List<ProductModel> productModels;


}
