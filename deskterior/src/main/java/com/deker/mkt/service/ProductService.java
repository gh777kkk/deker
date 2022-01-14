package com.deker.mkt.service;

import com.deker.mkt.model.*;

import java.util.List;

public interface ProductService {

    public List<ProductModel> getBestSaleProductList();


    ProductCategory getCategoryList(String code);



    public List<ProductDetailModel> getProductDetail(String productId);
    public List<ProductDetailExplain> getProductDetailExplain(String productId);
    public List<RecommendedProduct> getRecommendedProduct(String categoryId);
    public List<ProductReview> getProductReview(String productId);


    public void insertProductCart(ProductCart pc);
    public void insertRecentProduct(ProductCode pc);

    public ProductBuyOption getProductBuyList(ProductBuy pb);



}
