package com.deker.mkt.model.response;

import com.deker.mkt.model.MarketCategory;
import com.deker.mkt.model.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MarketMainModel {

    private List<ProductModel> productModels;
    private List<MarketCategory> marketCategories;


}
