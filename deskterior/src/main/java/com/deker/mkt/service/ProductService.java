package com.deker.mkt.service;

import com.deker.mkt.model.*;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.response.ProductBuyOption;
import com.deker.mkt.model.response.ProductCategory;
import com.deker.mkt.model.response.ProductDetail;
import com.deker.mkt.model.resultService.ProductDetailExplain;
import com.deker.mkt.model.resultService.ProductDetailModel;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.model.resultService.RecommendedProduct;

import java.util.List;

public interface ProductService {

    public List<ProductModel> getBestSaleProductList();


    ProductCategory getCategoryList(String code);


    ProductDetail getProductDetails(ProductCode pc);



    public void insertRecentProduct(ProductCode pc);

    public void insertProductCart(ProductCart pc);
    public ProductBuyOption getProductBuyList(ProductBuy pb);



}
