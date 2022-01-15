package com.deker.mkt.model.request;

import com.deker.mkt.model.ProductOption;
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
