package com.deker.mkt.service;

import com.deker.cmm.model.Menu;
import com.deker.cmm.model.PageInfo;
import com.deker.cmm.model.PageReview;
import com.deker.cmm.model.PagingConditions;
import com.deker.exception.AddressIdNotFoundException;
import com.deker.exception.MyAddressListOverException;
import com.deker.exception.TrackingException;
import com.deker.exception.TrackingKeyException;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.mapper.ProductMapper;

import com.deker.mkt.model.*;

import com.deker.mkt.model.request.*;
import com.deker.mkt.model.response.*;

import com.deker.mkt.model.resultService.*;
import com.deker.post.model.Post;
import com.deker.post.model.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    public PageInfo<ProductModel> getMoreBestSaleProductList(PagingConditions pc){



        int nonpagedCount = productMapper.getProductCount();
        PageInfo<ProductModel> pageInfo = new PageInfo<>(pc,nonpagedCount);

        pageInfo.setList(productMapper.getMoreBestSaleProductList(pc));


        return pageInfo;
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
        String n = productMapper.getCategoryId(pc.getProductId());

        pc.setCategoryId(n);
        List<RecommendedProduct> recommendedProducts = productMapper.getRecommendedProduct(pc.getCategoryId());

        for (RecommendedProduct recommendedProduct : recommendedProducts) {
            recommendedProduct.setProductImg(CMMUtil.getImg(recommendedProduct.getProductImg()));
        }

        pd.setRecommendedProduct(recommendedProducts);

        return pd;
    }




    public PageReview<ProductReview> getProductReview(ProductReview pr) throws ParseException {


        int nonpagedCount = productMapper.getProductReviewCount(pr);
        PageReview<ProductReview> pageInfo = new PageReview<>(pr,nonpagedCount);

        List<ProductReview> reviews = productMapper.getProductReview(pr);


        for (ProductReview review : reviews) {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateParser.parse(review.getReviewDate());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String myDate = dateParser.format(date);
            review.setReviewDate(myDate);


            review.setProductImg(CMMUtil.getImg(review.getProductImg()));
            review.setProReviewImg(CMMUtil.getImg(review.getProReviewImg()));

            List<String> options = new ArrayList<String>();
            options.add(review.getOption1DataName());
            options.add(review.getOption2DataName());
            review.setProductOption(options);


        }

        //pd.setReviews(reviews);

        pageInfo.setList(reviews);


        return pageInfo;
    }




    public void insertRecentProduct(ProductCode pc){
        RecentProduct rp = new RecentProduct();
        rp.setMktRecentProductId(CMMUtil.nextId("mrpId"));
        rp.setProductId(pc.getProductId());
        rp.setMemId(pc.getMemId());
        productMapper.insertRecentProduct(rp);
    }



    public void insertProductCart(List<ProductOption> pOption, String memId){

        for (ProductOption productOption : pOption) {
            ProductOption po = productMapper.getProductOptionId(productOption);
            productOption.setProductOptionId(po.getProductOptionId());
            productOption.setProductPrice(po.getProductPrice());
            System.out.println(po.getProductOptionId());
            int quantity = productOption.getOrderQuantity();
            productOption.setProductPrice(quantity* productOption.getProductPrice());
            productOption.setMemId(memId);
            productOption.setMktCartId(CMMUtil.nextId("cartId"));
            productMapper.insertProductCart(productOption);
        }

    }



    public ProductCartItems getCartList(String memId){
        List<ProductCartItems> productCartItems = productMapper.getCartList(memId);
        for (ProductCartItems pc : productCartItems) {
            pc.setProductImg(CMMUtil.getImg(pc.getProductImg()));
            List<ProductDetailOption> pdo = productMapper.getProductDetailOption(pc.getMktProductId());
            pc.setProductDetailOption(pdo.get(0));

        }
        ProductCartItems result = new ProductCartItems();
        result.setProductCartItems(productCartItems);

        return result;
    }



    public ProductCode insertBuyCartList(ProductCode pc){

        String myOrderId = CMMUtil.nextId("myOdId");

        for(String id : pc.getCartIdArr()){
            ProductCartToOderItem item = productMapper.getCheckedCart(id);

            item.setOrderItemId(CMMUtil.nextId("ordId"));
            item.setMemId(pc.getMemId());
            productMapper.insertOrderItem(item);

            item.setMyOderId(myOrderId);
            productMapper.insertMyOrderItem(item);

        }

        pc.setOrderId(myOrderId);

        return pc;
    }



    public ProductBuyOption getProductBuyList(ProductBuy pb){

        ProductBuyOption pbo = new ProductBuyOption();
        List<ProductOption> oList = pb.getProductOption();

        for (ProductOption productOption : oList) {
            //productOption.setProductOption(productMapper.getProductOptionId(productOption));
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


    public PageInfo<MyShoppingList> getOrderProduct(MyShoppingConditions conditions){
        MyShopping myShopping = new MyShopping();
        List<MyShoppingOrderState> orderState = productMapper.selectMyShoppingOrderState(conditions);
        if (orderState != null){
            for (MyShoppingOrderState data : orderState){
                if (data.getOrderState().equals("7")) myShopping.setDeposit(data.getOrderStateCount());
                if (data.getOrderState().equals("8")) myShopping.setPayment(data.getOrderStateCount());
                if (data.getOrderState().equals("1")) myShopping.setPreparation(data.getOrderStateCount());
                if (data.getOrderState().equals("2")) myShopping.setDelivery(data.getOrderStateCount());
                if (data.getOrderState().equals("3")) myShopping.setDelivery(data.getOrderStateCount());
                if (data.getOrderState().equals("4")) myShopping.setDelivery(data.getOrderStateCount());
                if (data.getOrderState().equals("5")) myShopping.setDelivery(data.getOrderStateCount());
                if (data.getOrderState().equals("6")) myShopping.setComplete(data.getOrderStateCount());
                if (data.getOrderState().equals("9")) myShopping.setFinish(data.getOrderStateCount());
            }
        }
        List<MyShoppingList> shoppingList = productMapper.selectMyShoppingList(conditions);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        for (MyShoppingList data : shoppingList) {
            List<String> optionList = new ArrayList<>();
            data.setProductImg(CMMUtil.getImg(data.getProductImg()));
            data.setStringDt(dateFormat.format(data.getCreateDt()));
            if (data.getOption1() != null) optionList.add(data.getOption1DataNm());
            if (data.getOption2() != null) optionList.add(data.getOption2DataNm());
            data.setOptionList(optionList);
            data.setOrderNumber(data.getOrderId().substring(6));
        }

        myShopping.setOrderList(shoppingList);

        int nonpagedCount = productMapper.selectMyShoppingListCount(conditions);
        PageInfo<MyShoppingList> pageInfo = new PageInfo<>(conditions,nonpagedCount);
        pageInfo.setList(myShopping.getOrderList());
        myShopping.setOrderList(null);
        pageInfo.setObjectData(myShopping);

        return pageInfo;
    }

    public List<MyAddress> getMyAddressList(String memId){
        return productMapper.selectMyAddressList(memId);
    }

    public void regMyAddress(MyAddressConditions conditions) throws Exception{
        myAddressValidation(conditions);
        conditions.setAddId(CMMUtil.nextId("addId"));
        productMapper.insertMyAddress(conditions);
    }

    public void modMyAddress(MyAddressConditions conditions) throws Exception{
        myAddressValidation(conditions);
        productMapper.deleteMyAddress(conditions);
        conditions.setAddId(CMMUtil.nextId("addId"));
        productMapper.insertMyAddress(conditions);
    }

    public void rmvMyAddress(MyAddressConditions conditions) throws Exception{
        int cnt = productMapper.selectMyAddressIdCount(conditions);
        if (cnt == 0) throw new AddressIdNotFoundException();
        productMapper.deleteMyAddress(conditions);
    }

    private void myAddressValidation(MyAddressConditions conditions) throws Exception{
        int cnt = productMapper.selectMyAddressIdCount(conditions);
        if (cnt == 0) throw new AddressIdNotFoundException();
        List<MyAddress> myAddressList = productMapper.selectMyAddressList(conditions.getMemId());
        if (myAddressList != null && myAddressList.size()>=3) throw new MyAddressListOverException();
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