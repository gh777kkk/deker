package com.deker.mkt.mapper;

import com.deker.mkt.model.ProductModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductModel> getBestSaleProductList();

    List<ProductModel> getBestCategoryProductList(String code);
    List<ProductModel> getNewCategoryProductList(String code);

}

