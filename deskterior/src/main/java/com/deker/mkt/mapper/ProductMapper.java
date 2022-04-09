package com.deker.mkt.mapper;

import com.deker.cmm.model.Menu;
import com.deker.cmm.model.PagingConditions;
import com.deker.mkt.model.*;
import com.deker.mkt.model.request.*;
import com.deker.mkt.model.response.*;
import com.deker.mkt.model.resultService.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductModel> getBestSaleProductList();
    List<MarketCategory> getProductCategory();

    List<ProductModel> getMoreBestSaleProductList(PagingConditions pc);
    int getProductCount();

    List<ProductModel> getBestCategoryProductMore(PagingConditions conditions);
    int getProductCategoryCount(String categoryId);


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
    List<ProductCartItems> getCartList(String memId);
    ProductCartToOderItem getCheckedCart(String id);
    void insertOrderItem(ProductCartToOderItem item);
    void insertMyOrderItem(ProductCartToOderItem item);

    void insertRecentProduct(RecentProduct pp);

    ProductBuyOption getProductBuyList(ProductBuy pb);
    MarketAddress getAddress(String memId);

    ProductOption getProductOptionId(ProductOption po);

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
    ArrayList<MyShoppingList> selectMyShoppingList(MyShoppingConditions conditions);
    Integer selectMyShoppingListCount(MyShoppingConditions conditions);

    List<MyAddress> selectMyAddressList(String memId);
    void insertMyAddress(MyAddressConditions conditions);
    void deleteMyAddress(MyAddressConditions conditions);
    Integer selectMyAddressIdCount(MyAddressConditions conditions);
    void deleteMyAddMain(MyAddressConditions conditions);
    void insertMyAddMain(MyAddressConditions conditions);

    List<ProductDetailModel> mbGetRecentProduct(String memId);

    RecentProduct mbGetRecentProductCheck(ProductCode pc);
    void updateRecentProductDate(RecentProduct rp);

    int getProductPrice(String productOptionId);


    MarketAddress getMainAddress(String memId);
    List<ProductOption> getProductOption(String orderId);

    List<MyShoppingOrderState> selectOrderState(OrderConditions conditions);
    void updateOrderStateCompleted(OrderConditions conditions);


    void deletCartId(String id);
    void modOption(Payment pm);
    void insertAddress(String addId);

}

