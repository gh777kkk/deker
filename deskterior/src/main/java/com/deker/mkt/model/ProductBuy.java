package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductBuy {

    private String memId;
    private int Price;
    private List<ProductOption> productOption;


}
