package com.deker.mkt.model.response;

import com.deker.mkt.model.ProductModel;
import com.deker.mkt.model.resultService.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductDetail {

    private ProductDetailModel productDetail;
    private List<ProductDetailOption> productDetailOption;
    private List<ProductDetailExplain> productDetailExplain;
    private List<RecommendedProduct> recommendedProduct;
    private List<ProductReview> productReview;


}
