package com.deker.mkt.service;

import com.deker.mkt.model.ProductModel;

import java.util.List;

public interface ProductService {
    public List<ProductModel> getBestSaleProductList();

    public List<ProductModel> getBestCategoryProductList(String code);
    public List<ProductModel> getNewCategoryProductList(String code);

}
