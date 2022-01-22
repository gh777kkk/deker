package com.deker.mkt.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyShopping {
    private int deposit;
    private int payment;
    private int preparation;
    private int delivery;
    private int complete;
    private int finish;
    private List<ProductTracking> productList;
}
