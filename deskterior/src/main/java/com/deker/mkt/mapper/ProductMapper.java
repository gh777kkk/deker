package com.deker.mkt.mapper;

import com.deker.mkt.model.*;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductOrder;
import com.deker.mkt.model.response.ProductBuyOption;
import com.deker.mkt.model.response.ProductTracking;
import com.deker.mkt.model.response.RecentProduct;
import com.deker.mkt.model.resultService.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductModel> getBestSaleProductList();
    List<MarketCategory> getProductCategory();

    List<ProductModel> getBestCategoryProductList(String codeId);
    List<ProductModel> getNewCategoryProductList(String codeId);

    ProductDetailModel getProductDetail(String productId);
    List<ProductDetailOption> getProductDetailOption(String productId);
    List<ProductDetailExplain> getProductDetailExplain(String productId);
    List<RecommendedProduct> getRecommendedProduct(String categoryId);
    List<ProductReview> getProductReview(String productId);

    void insertProductCart(ProductOption pc);
    void insertRecentProduct(RecentProduct pp);

    ProductBuyOption getProductBuyList(ProductBuy pb);
    MarketAddress getAddress(String memId);

    String getProductOptionId(ProductOption po);

    ProductTracking selectProductTracking(ProductOrder conditions);
    String selectLevelCodeNm(String level);

    List<DeliveryStatus> selectDeliveryStatus();

    void updateOrderState(DeliveryUpdate du);
    void updateCompletedDate(DeliveryUpdate du);

    List<DeliveryUpdate> selectDeliveryComplete();
    void updateOrderConfirm(DeliveryUpdate du);

    void regReview(ProductReview pr);
    void modReview(ProductReview pr);
}

