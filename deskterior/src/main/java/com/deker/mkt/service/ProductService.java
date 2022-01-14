package com.deker.mkt.service;

import com.deker.mkt.model.*;

import java.util.List;

public interface ProductService {
    public List<ProductModel> getBestSaleProductList();

    public List<ProductModel> getBestCategoryProductList(String code);
    public List<ProductModel> getNewCategoryProductList(String code);

    public List<ProductDetailModel> getProductDetail(String productId);
    public List<ProductDetailExplain> getProductDetailExplain(String productId);
    public List<RecommendedProduct> getRecommendedProduct(String categoryId);
    public List<ProductReview> getProductReview(String productId);


    public List<ProductCart> regProductCart(ProductCart pc);


}
