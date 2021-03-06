package com.deker.mkt.service;

import com.deker.cmm.model.Menu;
import com.deker.cmm.model.PageInfo;
import com.deker.cmm.model.PageReview;
import com.deker.cmm.model.PagingConditions;
import com.deker.exception.*;
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
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

        List<ProductModel> myList = productMapper.getMoreBestSaleProductList(pc);
        for (ProductModel pm : myList) {
            pm.setProductImg(CMMUtil.getImg(pm.getProductImg()));
            }


        pageInfo.setList(myList);


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


    public PageInfo<ProductModel> getMoreCategoryList(ProductCategoryConditions conditions){

        int nonpagedCount = productMapper.getProductCategoryCount(conditions.getCategoryId());
        PageInfo<ProductModel> pageInfo = new PageInfo<>(conditions,nonpagedCount);
        List<ProductModel> categoryProduct =productMapper.getBestCategoryProductMore(conditions);

        for (ProductModel cp : categoryProduct) {
            cp.setProductImg(CMMUtil.getImg(cp.getProductImg()));
        }

        pageInfo.setList(categoryProduct);

        return pageInfo;
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




    public ProductCode getBuyNow(List<ProductOption> pOption, String memId ){
        ProductCode pc = new ProductCode();
        String myOrderId = CMMUtil.nextId("myOdId");

        ProductCartToOderItem item= new ProductCartToOderItem();
        List<ProductCartToOderItem> items = new ArrayList<ProductCartToOderItem>();

        HashMap<String, Integer> dPay = new HashMap<String, Integer>();

        for(ProductOption option : pOption){
            item.setProductOptionId(option.getProductOptionId());
            item.setMktProductId(option.getMktProductId());
            item.setTotalPrice(productMapper.getProductPrice(option.getProductOptionId())*option.getOrderQuantity());


            if(dPay.get("deker")==null){
                dPay.put("deker",item.getTotalPrice());
            }
            else{
                dPay.put("deker",item.getTotalPrice()+dPay.get("deker"));
            }

            items.add(item);



        }

        for(ProductCartToOderItem myItem : items){


            myItem.setOrderItemId(CMMUtil.nextId("ordId"));
            myItem.setMemId(memId);

            if(dPay.get("deker")>=30000){
                myItem.setDeliveryPay(0);
            }
            else{
                myItem.setDeliveryPay(2500);
            }
            productMapper.insertOrderItem(myItem);

            myItem.setMyOderId(myOrderId);
            productMapper.insertMyOrderItem(myItem);

        }



        pc.setOrderId(myOrderId);

        return pc;
    }










    public void insertProductCart(List<ProductOption> pOption, String memId) throws Exception{

        if(pOption.size()==0) throw new FailedAddCart();
        else {
            for (ProductOption productOption : pOption) {
                ProductOption po = productMapper.getProductOptionId(productOption);
                productOption.setProductOptionId(po.getProductOptionId());
                productOption.setProductPrice(po.getProductPrice());
                System.out.println(po.getProductOptionId());
                int quantity = productOption.getOrderQuantity();
                productOption.setProductPrice(quantity * productOption.getProductPrice());
                productOption.setMemId(memId);
                productOption.setMktCartId(CMMUtil.nextId("cartId"));
                productMapper.insertProductCart(productOption);
            }
        }

    }



    public ProductCartItems getCartList(String memId){
        List<ProductCartItems> productCartItems = productMapper.getCartList(memId);
        for (ProductCartItems pc : productCartItems) {
            System.out.println(pc.getProductImg());
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

        ProductCartToOderItem item= new ProductCartToOderItem();
        List<ProductCartToOderItem> items = new ArrayList<ProductCartToOderItem>();

        HashMap<String, Integer> dPay = new HashMap<String, Integer>();

        for(String id : pc.getCartIdArr()){
            item = productMapper.getCheckedCart(id);

            if(dPay.get("deker")==null){
                dPay.put("deker",item.getTotalPrice());
            }
            else{
                dPay.put("deker",item.getTotalPrice()+dPay.get("deker"));
            }

            items.add(item);



        }

        for(ProductCartToOderItem myItem : items){


            myItem.setOrderItemId(CMMUtil.nextId("ordId"));
            myItem.setMemId(pc.getMemId());

            if(dPay.get("deker")>=30000){
                myItem.setDeliveryPay(0);
            }
            else{
                myItem.setDeliveryPay(2500);
            }

            productMapper.insertOrderItem(myItem);

            myItem.setMyOderId(myOrderId);
            productMapper.insertMyOrderItem(myItem);

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
            if (data == null || data.getBody() == null) throw new TrackingException("?????? ??????");
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

        if (img != null) {
            String reviewId = CMMUtil.setImg(img, pr.getMemId());
            pr.setProReviewImg(reviewId);
        }

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


    public PageInfo<MyShoppingItem> getOrderProduct(MyShoppingConditions conditions) throws Exception{
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

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        switch (conditions.getPeriod()) {
            case "day":
                cal.add(Calendar.DATE, -1);
                conditions.setPeriod(format.format(cal.getTime()));
                break;
            case "week":
                cal.add(Calendar.DATE, -7);
                conditions.setPeriod(format.format(cal.getTime()));
                break;
            case "month":
                cal.add(Calendar.MONTH, -1);
                conditions.setPeriod(format.format(cal.getTime()));
                break;
            case "half_year":
                cal.add(Calendar.MONTH, -6);
                conditions.setPeriod(format.format(cal.getTime()));
                break;
            case "year":
                cal.add(Calendar.YEAR, -1);
                conditions.setPeriod(format.format(cal.getTime()));
                break;
            default: conditions.setPeriod("");
                break;
        }
//        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        ArrayList<MyShoppingList> shoppingList = productMapper.selectMyShoppingList(conditions);

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

        ArrayList<MyShoppingItem> myShoppingItems = new ArrayList<>();

        while (shoppingList.size() > 0) {
            String myOrderId = shoppingList.get(0).getMyOrderId();
            ArrayList<MyShoppingList> myShoppingList = new ArrayList<>();
            MyShoppingItem item = new MyShoppingItem();
            item.setOrderNumber(myOrderId.substring(7));
            for (int i = 0; i < shoppingList.size();){
                if (myOrderId.equals(shoppingList.get(i).getMyOrderId())){
                    myShoppingList.add(shoppingList.get(i));
                    shoppingList.remove(i);
                }else i++;
            }
            item.setOrderList(myShoppingList);
            myShoppingItems.add(item);
        }

        myShopping.setOrderList(myShoppingItems);
        if (myShopping.getOrderList().size() == 0) throw new OrderNullPintException();

        int nonpagedCount = productMapper.selectMyShoppingListCount(conditions);
        PageInfo<MyShoppingItem> pageInfo = new PageInfo<>(conditions,nonpagedCount);
        pageInfo.setList(myShopping.getOrderList());
        myShopping.setOrderList(null);
        pageInfo.setObjectData(myShopping);

        return pageInfo;
    }

    public List<MyAddress> getMyAddressList(String memId){
        return productMapper.selectMyAddressList(memId);
    }

    public void regMyAddress(MyAddressConditions conditions) throws Exception{
        List<MyAddress> myAddressList = productMapper.selectMyAddressList(conditions.getMemId());
        if (myAddressList != null && myAddressList.size()>=3) throw new MyAddressListOverException();
        conditions.setAddId(CMMUtil.nextId("addId"));
        productMapper.insertMyAddress(conditions);
    }

    public void modMyAddress(MyAddressConditions conditions) throws Exception{
        int cnt = productMapper.selectMyAddressIdCount(conditions);
        if (cnt == 0) throw new AddressIdNotFoundException();
        List<MyAddress> myAddressList = productMapper.selectMyAddressList(conditions.getMemId());
        if (myAddressList != null && myAddressList.size()>=3) throw new MyAddressListOverException();
        productMapper.deleteMyAddress(conditions);
        conditions.setAddId(CMMUtil.nextId("addId"));
        productMapper.insertMyAddress(conditions);
    }

    public void rmvMyAddress(MyAddressConditions conditions) throws Exception{
        int cnt = productMapper.selectMyAddressIdCount(conditions);
        if (cnt == 0) throw new AddressIdNotFoundException();
        productMapper.deleteMyAddress(conditions);
    }

    public void modAddressMain(MyAddressConditions conditions) throws Exception{
        int cnt = productMapper.selectMyAddressIdCount(conditions);
        if (cnt == 0) throw new AddressIdNotFoundException();
        productMapper.deleteMyAddMain(conditions);
        productMapper.insertMyAddMain(conditions);
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




    public void insertRecentProduct(ProductCode pc){

        RecentProduct r = productMapper.mbGetRecentProductCheck(pc);

        if(r==null){
            RecentProduct rp = new RecentProduct();
            rp.setMktRecentProductId(CMMUtil.nextId("mrpId"));
            rp.setProductId(pc.getProductId());
            rp.setMemId(pc.getMemId());
            productMapper.insertRecentProduct(rp);
        }
        else{
            productMapper.updateRecentProductDate(r);

        }

    }


    public List<ProductDetailModel> mbGetRecentProduct(String memId){

        List<ProductDetailModel> recentList = productMapper.mbGetRecentProduct(memId);
        for(ProductDetailModel pd : recentList){
            pd.setProductImg(CMMUtil.getImg(pd.getProductImg()));
        }

        return recentList;

    }



    public Object nmbRegRecentProduct(String productId, HttpSession session){


            List myArr = (List) session.getAttribute("productId");
            if(myArr!=null) {
                int num = myArr.indexOf(productId);
                if (num == -1) {
                    myArr.add(productId);
                } else {
                    myArr.remove(num);
                    myArr.add(productId);

                }
                session.setAttribute("productId", myArr);
            }
            else{

            myArr = new ArrayList<>();
            myArr.add(productId);

            int num = myArr.indexOf(productId);
            if(num==-1){
                myArr.add(productId);
            }
            else{
                myArr.remove(num);
                myArr.add(productId);

            }
            session.setAttribute("productId", myArr);

             }

            return session.getId();


    }


    public List<ProductDetailModel> nmbGetRecentProduct(HttpSession session) throws Exception {

        //HttpSession session = request.getSession(false);
        List<ProductDetailModel> recentList = new ArrayList<>();

        List<String> idArr = (List)session.getAttribute("productId");

        if(idArr == null) throw new RecentProductNotFound();
        else{

            Collections.reverse(idArr);


            for (int i =0; i<idArr.size(); i++){
                if(recentList.size()==10){
                    break;
                }
                else {
                    ProductDetailModel pd = productMapper.getProductDetail(idArr.get(i));
                    pd.setProductImg(CMMUtil.getImg(pd.getProductImg()));
                    recentList.add(pd);
                }
            }
        }



        return recentList;

    }


    public OrderList getOrderList(OrderList orderList){

        int totalPrice=0;
        orderList.setMarketAddress(productMapper.getMainAddress(orderList.getMemId()));
        List<ProductOption> po = productMapper.getProductOption(orderList.getOrderId());
        for(ProductOption mypo: po){
            mypo.setProductImg(CMMUtil.getImg(mypo.getProductImg()));
            totalPrice+=(mypo.getProductPrice()*mypo.getOrderQuantity());
        }
        orderList.setTotalDeliveryPay(po.get(0).getDeliveryPay());
        orderList.setTotalPrice(totalPrice);
        orderList.setProductOption(po);

        return orderList;
    }






    public void modDeliveryCompleted(OrderConditions conditions) throws Exception {
        List<MyShoppingOrderState> a = productMapper.selectOrderState(conditions);
        if (productMapper.selectOrderState(conditions).size()<1) throw new OrderIdNotFoundException();
        productMapper.updateOrderStateCompleted(conditions);
    }


    public void modProduct(Payment pm){
        if(pm.getCartIdArr()!=null){
            for(String id : pm.getCartIdArr()){
                productMapper.deletCartId(id);
            }
        }
        for(int i=0; i<pm.getProductOptionId().size(); i++){
            pm.setOptionId(pm.getProductOptionId().get(i));
            pm.setQuantity(pm.getOrderQuantity().get(i));
            productMapper.modOption(pm);
        }
        List<String> orderItem = productMapper.getItemOrderId(pm);
        for(String id: orderItem){
            pm.setItemOrderId(id);
            productMapper.insertAddress(pm);
        }

    }



    public List<ReviewItem> getReviewableItem(ReviewItem ri) {

        List<ReviewItem> myList = productMapper.getReviewableItem(ri.getMemId());

        for (ReviewItem r : myList){
            r.setProductImg(CMMUtil.getImg(r.getProductImg()));
            r.setProductTotalPrice(r.getProductTotalPrice()+r.getDeliveryPay());
        }
        return myList;
    }


    public List<ReviewItem> getReviewedItem(ReviewItem ri) {

        List<ReviewItem> myList = productMapper.getReviewedItem(ri.getMemId());

        for (ReviewItem r : myList){
            r.setProductImg(CMMUtil.getImg(r.getProductImg()));
            r.setProductTotalPrice(r.getProductTotalPrice()+r.getDeliveryPay());
        }
        return myList;

    }

    public void deleteCartItem(ProductCode pc){

        for(String id : pc.getCartIdArr()){
            productMapper.deletCartId(id);
        }
    }
}