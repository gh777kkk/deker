package com.deker.mkt.mapper;

import com.deker.mkt.model.ProductModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductModel> getBestSaleProductList();

    List<ProductModel> getDecoProductList();
    List<ProductModel> getNewDecoProductList();

    List<ProductModel> getFurnitureProductList();
    List<ProductModel> getNewFurnitureProductList();

    List<ProductModel> getHomeAppliancesProductList();
    List<ProductModel> getNewHomeAppliancesProductList();

    List<ProductModel> getLampProductList();
    List<ProductModel> getNewLampProductList();

    List<ProductModel> getStationeryProductList();
    List<ProductModel> getNewStationeryProductList();
}

