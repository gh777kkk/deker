package com.deker.mkt.service;

import com.deker.exception.TrackingException;
import com.deker.exception.TrackingKeyException;
import com.deker.mkt.mapper.ProductMapper;

import com.deker.mkt.model.*;
//import com.sun.mail.imap.protocol.Item;

import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.request.ProductOrder;
import com.deker.mkt.model.response.*;
import com.deker.mkt.model.resultService.ProductDetailExplain;
import com.deker.mkt.model.resultService.ProductDetailModel;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.model.resultService.RecommendedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

    public ProductTracking getProductTracking(ProductOrder conditions) throws Exception{
        ProductTracking result = productMapper.selectProductTracking(conditions);
        List<String> optionList = new ArrayList<>();
        if (result == null) return null;
        if (result.getOption1() != null) optionList.add(result.getOption1Nm() +" : "+result.getOption1DataNm());
        if (result.getOption2() != null) optionList.add(result.getOption2Nm() +" : "+result.getOption2DataNm());
        result.setOptionList(optionList);
        TrackingData trackingData = getTracking(result.getDeliveryCode(),result.getWaybill());
        result.setTrackingList(trackingData.getTrackingDetails());
        result.setProductImg(CMMUtil.getImg(result.getProductImg()));
        return result;

    }


    public TrackingData getTracking(String tCode,String tInvoice) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        try{
            String url = "http://info.sweettracker.co.kr/api/v1/trackingInfo?t_key="+trackingKey+"&t_code="+tCode+"&t_invoice="+tInvoice;
            ResponseEntity<TrackingData> data = restTemplate.getForEntity(url, TrackingData.class);
            if (data == null || data.getBody() == null) throw new TrackingException("조회 오류");
            if (data.getBody().getCode() != null) throw new TrackingException(data.getBody().getMsg());
            for (int idx = 0; data.getBody().getTrackingDetails().size() > idx; idx++){
                data.getBody().getTrackingDetails().get(idx).setLevelNm(productMapper.selectLevelCodeNm(Integer.toString(data.getBody().getTrackingDetails().get(idx).getLevel())));
            }
            return data.getBody();
        }catch (HttpClientErrorException e){
            throw new TrackingKeyException();
        }
    }

}
