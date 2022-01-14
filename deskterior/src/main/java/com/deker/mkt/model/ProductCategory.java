package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductCategory {

    private List<ProductModel> bestProduct;
    private List<ProductModel> newProduct;

}
