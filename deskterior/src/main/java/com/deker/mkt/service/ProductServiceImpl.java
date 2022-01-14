package com.deker.mkt.service;

import com.deker.mkt.mapper.ProductMapper;

import com.deker.mkt.model.*;
//import com.sun.mail.imap.protocol.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
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




    public List<ProductDetailModel> getProductDetail(String productId){

        return productMapper.getProductDetail(productId);
    }
    public List<ProductDetailExplain> getProductDetailExplain(String productId){

        return productMapper.getProductDetailExplain(productId);
    }
    public List<RecommendedProduct> getRecommendedProduct(String categoryId){

        return productMapper.getRecommendedProduct(categoryId);
    }
    public List<ProductReview> getProductReview(String productId){

        return productMapper.getProductReview(productId);
    }

    public void insertRecentProduct(ProductCode pc){
        RecentProduct rp = new RecentProduct();
        rp.setMktRecentProductId(CMMUtil.nextId("mrpId"));
        rp.setProductId(pc.getProductId());
        rp.setMemId(pc.getMemId());
    }

    public void insertProductCart(ProductCart pc){

        pc.setMktCartId(CMMUtil.nextId("cartId"));
         productMapper.insertProductCart(pc);
    }



    public ProductBuyOption getProductBuyList(ProductBuy pb){

        ProductBuyOption pbo = new ProductBuyOption();
        pbo.setPrice(pb.getPrice());
        pbo.setProductOption(pb.getProductOption());
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
