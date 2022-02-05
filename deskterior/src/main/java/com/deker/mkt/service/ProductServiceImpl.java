package com.deker.mkt.service;

import com.deker.cmm.model.Menu;
import com.deker.exception.TrackingException;
import com.deker.exception.TrackingKeyException;
import com.deker.mkt.mapper.ProductMapper;

import com.deker.mkt.model.*;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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



    public MarketMainModel getBestSaleProductList(){

        MarketMainModel marketMainModel = new MarketMainModel();
        List<ProductModel> productModels = productMapper.getBestSaleProductList();
        List<MarketCategory> marketCategories = productMapper.getProductCategory();

        for (ProductModel productModel : productModels) {
            productModel.setProductImg(CMMUtil.getImg(productModel.getProductImg()));
        }
        marketMainModel.setProductModels(productModels);

        for (MarketCategory marketCategory : marketCategories) {
            marketCategory.setCategoryImg(CMMUtil.getImg(marketCategory.getCategoryImg()));
        }
        marketMainModel.setMarketCategories(marketCategories);


        return marketMainModel;
    }



    public ProductCategory getCategoryList(String code){
        ProductCategory result = new ProductCategory();
        List<ProductModel> bestProducts =productMapper.getBestCategoryProductList(code);
        for (ProductModel bestProduct : bestProducts) {
            bestProduct.setProductImg(CMMUtil.getImg(bestProduct.getProductImg()));
        }
        result.setBestProduct(bestProducts);

        return result;
    }


    public ProductDetail getProductDetails(ProductCode pc) {

        ProductDetail pd = new ProductDetail();
        ProductDetailModel productDetail = productMapper.getProductDetail(pc.getProductId());
        List<ProductDetailExplain> productDetailExplains = productMapper.getProductDetailExplain(pc.getProductId());
        productDetail.setProductImg(CMMUtil.getImg(productDetail.getProductImg()));
        pd.setProductDetail(productDetail);
        pd.setProductDetailOption(productMapper.getProductDetailOption(pc.getProductId()));

        for (ProductDetailExplain productDetailExplain : productDetailExplains) {
            productDetailExplain.setDetailImg(CMMUtil.getImg(productDetailExplain.getDetailImg()));
        }
        pd.setProductDetailExplain(productDetailExplains);
        //pd.setRecommendedProduct(productMapper.getRecommendedProduct(pc.getCategoryId()));
        //pd.setProductReview(productMapper.getProductReview(pc.getProductId()));

        return pd;
    }




    public ProductDetail getRecoProduct(ProductCode pc) {

        ProductDetail pd = new ProductDetail();
        pc.setCategoryId(productMapper.getCategoryId(pc.getProductId()));
        List<RecommendedProduct> recommendedProducts = productMapper.getRecommendedProduct(pc.getProductId());


        for (RecommendedProduct recommendedProduct : recommendedProducts) {
            recommendedProduct.setProductImg(CMMUtil.getImg(recommendedProduct.getProductImg()));
        }

        pd.setRecommendedProduct(recommendedProducts);

        return pd;
    }




    public ProductDetail getProductReview(ProductReview pr) {

        ProductDetail pd = new ProductDetail();
        int end, start;

        start = pr.getPageNumber() * 100; //0부터 받아야 됨
        end = 100;
        pr.setStart(start);
        pr.setEnd(end);
        List<ProductReview> reviews = productMapper.getProductReview(pr);

        for (ProductReview review : reviews) {
            review.setProductImg(CMMUtil.getImg(review.getProductImg()));
        }

        for (ProductReview review : reviews) {
            review.setProReviewImg(CMMUtil.getImg(review.getProReviewImg()));
        }

        if(end > reviews.size()){
            pd.setLastPage(true);
        }

        pd.setReviews(reviews);

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
//        if (result.getOption1() != null) optionList.add(result.getOption1Nm() +" : "+result.getOption1DataNm());
//        if (result.getOption2() != null) optionList.add(result.getOption2Nm() +" : "+result.getOption2DataNm());
        if (result.getOption1() != null) optionList.add(result.getOption1DataNm());
        if (result.getOption2() != null) optionList.add(result.getOption2DataNm());
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
                data.getBody().getTrackingDetails().get(idx).setLevelNm(
                        productMapper.selectLevelCodeNm(Integer.toString(data.getBody().getTrackingDetails().get(idx).getLevel())));
            }

            return data.getBody();
        }catch (HttpClientErrorException e){
            throw new TrackingKeyException();
        }
    }




    public void regReview(ProductReview pr, MultipartFile img)throws Exception{
       pr.setProReviewImg(CMMUtil.setImg(img,pr.getMemId()));
       pr.setMktReviewId(CMMUtil.nextId("RVID"));
       productMapper.regReview(pr);

    }

    public void modReview(ProductReview pr, MultipartFile img)throws Exception{

        String reviewId = CMMUtil.setImg(img, pr.getMemId());
        pr.setProReviewImg(reviewId);

        productMapper.modReview(pr);

    }

    public ProductKeyword getRegProduct(ProductKeyword pk){


        List<ProductModel> productModels = productMapper.getRegProduct(pk);

        for (ProductModel productModel : productModels) {
            productModel.setProductImg(CMMUtil.getImg(productModel.getProductImg()));
        }

        pk.setProductModels(productModels);

        return pk;
    }




    @Async
    public void getTest() {
        try {

            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}