package com.deker.mkt.service;

import com.deker.mkt.mapper.ProductMapper;

import com.deker.mkt.model.*;
//import com.sun.mail.imap.protocol.Item;

import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.response.ProductBuyOption;
import com.deker.mkt.model.response.ProductCategory;
import com.deker.mkt.model.response.ProductDetail;
import com.deker.mkt.model.response.RecentProduct;
import com.deker.mkt.model.resultService.ProductDetailExplain;
import com.deker.mkt.model.resultService.ProductDetailModel;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.model.resultService.RecommendedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final com.deker.cmm.util.CMMUtil CMMUtil;

    @Value("${tracking.key}")
    private String trackingKey;

    public List<ProductModel> getBestSaleProductList(){

        return productMapper.getBestSaleProductList();
    }



    public ProductCategory getCategoryList(String code){
        ProductCategory result = new ProductCategory();
        result.setBestProduct(productMapper.getBestCategoryProductList(code));
        result.setNewProduct(productMapper.getNewCategoryProductList(code));

        return result;
    }


    public ProductDetail getProductDetails(ProductCode pc) {

        ProductDetail pd = new ProductDetail();
        pd.setProductDetail(productMapper.getProductDetail(pc.getProductId()));
        pd.setProductDetailOption(productMapper.getProductDetailOption(pc.getProductId()));
        pd.setProductDetailExplain(productMapper.getProductDetailExplain(pc.getProductId()));
        pd.setRecommendedProduct(productMapper.getRecommendedProduct(pc.getCategoryId()));
        pd.setProductReview(productMapper.getProductReview(pc.getProductId()));

        return pd;
    }


    public void insertRecentProduct(ProductCode pc){
        RecentProduct rp = new RecentProduct();
        rp.setMktRecentProductId(CMMUtil.nextId("mrpId"));
        rp.setProductId(pc.getProductId());
        rp.setMemId(pc.getMemId());
        productMapper.insertRecentProduct(rp);
    }



    public void insertProductCart(ProductCart pc){
        List<ProductOption> oList = pc.getProductOption();

        for (ProductOption productOption : oList) {
            productOption.setProductOption(productMapper.getProductOptionId(productOption));
            productOption.setMemId(pc.getMemId());
            productOption.setMktCartId(CMMUtil.nextId("cartId"));
            productMapper.insertProductCart(productOption);
        }

    }


    public ProductBuyOption getProductBuyList(ProductBuy pb){

        ProductBuyOption pbo = new ProductBuyOption();
        List<ProductOption> oList = pb.getProductOption();

        for (ProductOption productOption : oList) {
            productOption.setProductOption(productMapper.getProductOptionId(productOption));
        }

        pbo.setProductOption(oList);
        pbo.setPrice(pb.getResultPrice());
        pbo.setMarketAddress(productMapper.getAddress(pb.getMemId()));

        return pbo;
    }



//    public List<?> getTrackingInfo(){
//        List<?> result = new ArrayList<>();
//        Item[] a = getItemList();
//        return result;
//    }
//
//    private Item[] getItemList() {
//        String url = trackingKey + "/api/v1/companylist";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return restTemplate.getForObject(url, Item[].class);
//    }


}
