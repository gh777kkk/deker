package com.deker.mkt.service;

import com.deker.cmm.model.PageInfo;
import com.deker.cmm.model.PageReview;
import com.deker.cmm.model.PagingConditions;
import com.deker.mkt.model.*;
import com.deker.mkt.model.request.*;
import com.deker.mkt.model.response.*;
import com.deker.mkt.model.resultService.ProductDetailModel;
import com.deker.mkt.model.resultService.ProductReview;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;


public interface ProductService {

    MarketMainModel getBestSaleProductList();
    PageInfo<ProductModel> getMoreBestSaleProductList(PagingConditions pc);


    ProductCategory getCategoryList(String code);
    PageInfo<ProductModel> getMoreCategoryList(ProductCategoryConditions conditions);


    ProductDetail getProductDetails(ProductCode pc);
    ProductDetail getRecoProduct(ProductCode pc);
    PageReview<ProductReview> getProductReview(ProductReview pr) throws ParseException;


    void insertRecentProduct(ProductCode pc);

    ProductCode getBuyNow(List<ProductOption> po, String memId);

    void insertProductCart(List<ProductOption> po, String memId) throws Exception;
    ProductCartItems getCartList(String memId);
    ProductCode insertBuyCartList(ProductCode pc);



    ProductBuyOption getProductBuyList(ProductBuy pb);

    ProductTracking getProductTracking(ProductOrder conditions) throws Exception;
    TrackingData getTracking(String tCode,String tInvoice)throws Exception;

    void regReview(ProductReview pr, MultipartFile img) throws Exception;
    void modReview(ProductReview pr, MultipartFile img) throws Exception;

    ProductKeyword getRegProduct(ProductKeyword pk);

    void getTest();



    PageInfo<MyShoppingItem> getOrderProduct(MyShoppingConditions conditions)throws Exception;


    List<MyAddress> getMyAddressList(String memId);
    void regMyAddress(MyAddressConditions conditions) throws Exception;
    void modMyAddress(MyAddressConditions conditions) throws Exception;
    void rmvMyAddress(MyAddressConditions conditions) throws Exception;
    void modAddressMain(MyAddressConditions conditions) throws Exception;

    void nmbRegRecentProduct(String productId, HttpServletRequest request);
    List<ProductDetailModel> nmbGetRecentProduct(HttpServletRequest request);

    List<ProductDetailModel> mbGetRecentProduct(String memId);

    OrderList getOrderList(OrderList orderList);

    void modDeliveryCompleted(OrderConditions conditions) throws Exception;

    void modProduct(Payment pm);



    List<ReviewItem> getReviewableItem(ReviewItem ri);
    List<ReviewItem> getReviewedItem(ReviewItem ri);

    void deleteCartItem(ProductCode pc);

}
