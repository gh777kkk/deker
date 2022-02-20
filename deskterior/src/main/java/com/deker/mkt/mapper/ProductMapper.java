package com.deker.mkt.mapper;

import com.deker.cmm.model.Menu;
import com.deker.mkt.model.*;
import com.deker.mkt.model.request.MyShoppingConditions;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductOrder;
import com.deker.mkt.model.response.*;
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
    List<ProductReview> getProductReview(ProductReview pr);
    String getCategoryId(String productId);

    int getProductReviewCount(ProductReview pr);

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

    List<ProductModel> getRegProduct(ProductKeyword pk);



    List<Menu> getNmbMenu();
    List<Menu> getMbMenu();

    List<MyShoppingOrderState> selectMyShoppingOrderState(MyShoppingConditions conditions);
    List<MyShoppingList> selectMyShoppingList(MyShoppingConditions conditions);
    Integer selectMyShoppingListCount(MyShoppingConditions conditions);

}

