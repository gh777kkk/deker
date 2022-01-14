package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductCart {

    private String mktCartId;
    private String memId;
    private List<ProductOption> productOption;


}
