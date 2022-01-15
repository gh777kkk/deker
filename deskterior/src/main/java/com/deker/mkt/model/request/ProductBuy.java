package com.deker.mkt.model.request;

import com.deker.mkt.model.ProductOption;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductBuy {

    private String memId;
    private int resultPrice;
    private List<ProductOption> productOption;


}
