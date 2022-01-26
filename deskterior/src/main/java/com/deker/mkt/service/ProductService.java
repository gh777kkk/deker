package com.deker.mkt.service;

import com.deker.mkt.model.*;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.request.ProductOrder;
import com.deker.mkt.model.response.*;
import com.deker.mkt.model.resultService.ProductReview;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    MarketMainModel getBestSaleProductList();


    ProductCategory getCategoryList(String code);


    ProductDetail getProductDetails(ProductCode pc);



    void insertRecentProduct(ProductCode pc);

    void insertProductCart(ProductCart pc);
    ProductBuyOption getProductBuyList(ProductBuy pb);

    ProductTracking getProductTracking(ProductOrder conditions) throws Exception;
    TrackingData getTracking(String tCode,String tInvoice)throws Exception;

    void regReview(ProductReview pr, MultipartFile img) throws Exception;
    void modReview(ProductReview pr, MultipartFile img) throws Exception;

}
