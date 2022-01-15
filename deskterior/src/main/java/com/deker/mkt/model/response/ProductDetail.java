package com.deker.mkt.model.response;

import com.deker.mkt.model.ProductModel;
import com.deker.mkt.model.resultService.ProductDetailExplain;
import com.deker.mkt.model.resultService.ProductDetailModel;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.model.resultService.RecommendedProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductDetail {

    private List<ProductDetailModel> productDetail;
    private List<ProductDetailExplain> productDetailExplain;
    private List<RecommendedProduct> recommendedProduct;
    private List<ProductReview> productReview;


}
