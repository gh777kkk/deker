package com.deker.mkt.model.response;

import com.deker.mkt.model.resultService.MarketAddress;
import com.deker.mkt.model.ProductOption;
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
