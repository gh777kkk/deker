package com.deker.mkt.service;

import com.deker.mkt.model.ProductModel;

import java.util.List;

public interface ProductService {
    public List<ProductModel> getBestSaleProductList();

    public List<ProductModel> getDecoProductList();
    public List<ProductModel> getNewDecoProductList();

    public List<ProductModel> getFurnitureProductList();
    public List<ProductModel> getNewFurnitureProductList();

    public List<ProductModel> getHomeAppliancesProductList();
    public List<ProductModel> getNewHomeAppliancesProductList();

    public List<ProductModel> getLampProductList();
    public List<ProductModel> getNewLampProductList();

    public List<ProductModel> getStationeryProductList();
    public List<ProductModel> getNewStationeryProductList();
}
