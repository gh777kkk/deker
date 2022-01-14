package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductBuyOption {

    private int Price;
    private List<ProductOption> productOption;
    private MarketAddress marketAddress;


}
