package com.deker.mkt.service;

import com.deker.cmm.model.PageInfo;
import com.deker.cmm.model.PageReview;
import com.deker.cmm.model.PagingConditions;
import com.deker.mkt.model.*;
import com.deker.mkt.model.request.*;
import com.deker.mkt.model.response.*;
import com.deker.mkt.model.resultService.ProductReview;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;


public interface ProductService {

    MarketMainModel getBestSaleProductList();
    PageInfo<ProductModel> getMoreBestSaleProductList(PagingConditions pc);


    ProductCategory getCategoryList(String code);


    ProductDetail getProductDetails(ProductCode pc);
    ProductDetail getRecoProduct(ProductCode pc);
    PageReview<ProductReview> getProductReview(ProductReview pr) throws ParseException;


    void insertRecentProduct(ProductCode pc);

    void insertProductCart(List<ProductOption> po, String memId);
    ProductCartItems getCartList(String memId);
    ProductCode insertBuyCartList(ProductCode pc);



    ProductBuyOption getProductBuyList(ProductBuy pb);

    ProductTracking getProductTracking(ProductOrder conditions) throws Exception;
    TrackingData getTracking(String tCode,String tInvoice)throws Exception;

    void regReview(ProductReview pr, MultipartFile img) throws Exception;
    void modReview(ProductReview pr, MultipartFile img) throws Exception;

    ProductKeyword getRegProduct(ProductKeyword pk);

    void getTest();



    PageInfo<MyShoppingList> getOrderProduct(MyShoppingConditions conditions);





}
