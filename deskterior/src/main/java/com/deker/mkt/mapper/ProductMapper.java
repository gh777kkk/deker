package com.deker.mkt.mapper;

import com.deker.mkt.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductModel> getBestSaleProductList();

    List<ProductModel> getBestCategoryProductList(String codeId);
    List<ProductModel> getNewCategoryProductList(String codeId);

    List<ProductDetailModel> getProductDetail(String productId);
    List<ProductDetailExplain> getProductDetailExplain(String productId);
    List<RecommendedProduct> getRecommendedProduct(String productId);
    List<ProductReview> getProductReview(String productId);
}

