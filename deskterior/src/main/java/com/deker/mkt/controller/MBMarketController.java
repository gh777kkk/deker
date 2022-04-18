package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductOption;
import com.deker.mkt.model.request.*;
import com.deker.mkt.model.response.OrderList;
import com.deker.mkt.model.response.ReviewItem;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mb/mkt")
public class MBMarketController {

    public final ProductService productService;
    private final JwtProvider jwtProvider;
    private final IamportService iamportService;

    //reg, get, mod, del

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인",
                        productService.getBestSaleProductList())
        );
    }

    @RequestMapping(value = "/get/product/more", method = RequestMethod.POST)
    public ResponseEntity<?> getProductMore() {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인 더보기",
                        productService.getBestSaleProductList())
        );
    }




    @RequestMapping(value = "/get/category", method = RequestMethod.POST)
    public ResponseEntity<Result> getCategory(@RequestBody ProductCode pc) {

        return ResponseEntity.ok(
                new Result("200", "카테고리 목록",
                        productService.getCategoryList(pc.getCategoryId()))

        );
    }


    @RequestMapping(value = "/get/category/product/more", method = RequestMethod.POST)
    public ResponseEntity<?> getCategoryProductMore() {

        return ResponseEntity.ok(
                new Result("200", "스토어 카테고리 상품 더보기",
                        productService.getBestSaleProductList())
        );
    }




    @RequestMapping(value = "/get/product-detail", method = RequestMethod.POST)
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pc, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pc.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        productService.getProductDetails(pc))
        );
    }



    @RequestMapping(value = "/reg/recent-product", method = RequestMethod.POST)
    public ResponseEntity<?> regRecentProduct(@RequestBody ProductCode pc, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pc.setMemId(memId);
        productService.insertRecentProduct(pc);

        return ResponseEntity.ok(
                new Result("200", "최근 본 상품 등록"
                )
        );
    }



    @RequestMapping(value = "/get/recent-product", method = RequestMethod.POST)
    public ResponseEntity<?> getRecentProduct(HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);

        return ResponseEntity.ok(
                new Result("200", "최근 본 상품",
                        productService.mbGetRecentProduct(memId)

                )
        );
    }


    @RequestMapping(value = "/reg/buy-now", method = RequestMethod.POST)
    public ResponseEntity<?> getBuyNow(@RequestBody List<ProductOption> po, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);


        return ResponseEntity.ok(
                new Result("200", "바로 결제",
                        productService.getBuyNow(po, memId)
                )
        );
    }





    @RequestMapping(value = "/reg/add-cart", method = RequestMethod.POST)
    public ResponseEntity<?> regCart(@RequestBody List<ProductOption> po, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        productService.insertProductCart(po, memId);
        return ResponseEntity.ok(
                new Result("200", "마켓 장바구니 아이템 추가"
                )
        );
    }

    @RequestMapping(value = "/get/cart", method = RequestMethod.POST)
    public ResponseEntity<?> getCart( HttpServletRequest request) {

      String memId = jwtProvider.getMemIdFromJwtToken(request);


        return ResponseEntity.ok(
                new Result("200", "장바구니 목록",
                        productService.getCartList(memId)
                )
        );
    }


    @RequestMapping(value = "/reg/checked-cart", method = RequestMethod.POST)
    public ResponseEntity<?> getBuyList(@RequestBody ProductCode pc, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pc.setMemId(memId);
        return ResponseEntity.ok(
                new Result("200", "결제 페이지",
                        productService.insertBuyCartList(pc)
                )
        );
    }





    @RequestMapping(value = "/get/tracking", method = RequestMethod.POST)
    public ResponseEntity<?> getTracking(@RequestBody ProductOrder conditions) throws Exception {


        return ResponseEntity.ok(
                new Result("200", "배송 조회",
                        productService.getProductTracking(conditions)
                )
        );
    }




    @RequestMapping(value = "/get/verify", method = RequestMethod.POST)
    public ResponseEntity<?> getVerifyPayment(@RequestBody Payment pm) throws Exception {
        return ResponseEntity.ok(
                new Result("200", "결제 완료",
                        iamportService.getBuyerInfor(pm)
                )
        );
    }


    @RequestMapping(value = "/get/payment-complete", method = RequestMethod.POST)
    public ResponseEntity<?> getPaymentComplete(@RequestBody Payment pm, HttpServletRequest request) {

        //String memId = jwtProvider.getMemIdFromJwtToken(request);
        //pb.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "결제 완료"
                //상품 값 수정

                )
        );
    }



    @RequestMapping(value = "/reg/review", method = RequestMethod.POST)
    public ResponseEntity<?> regProductReview( @RequestParam(value = "myImg", required = false) MultipartFile myImg, @RequestPart("") ProductReview pr, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pr.setMemId(memId);
        productService.regReview(pr,myImg);

        return ResponseEntity.ok(
                new Result("200", "리뷰 작성"
                )
        );
    }



    @RequestMapping(value = "/mod/review", method = RequestMethod.POST)
    public ResponseEntity<?> modReview(@RequestParam(value = "myImg", required = false) MultipartFile myImg,
                                       @RequestBody ProductReview pr, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pr.setMemId(memId);
        productService.modReview(pr,myImg);

        return ResponseEntity.ok(
                new Result("200", "리뷰 수정 완료"
                )
        );
    }

    @RequestMapping(value = "/get/order-product", method = RequestMethod.POST)
    public ResponseEntity<?> getOrderProduct(@RequestBody MyShoppingConditions conditions, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "주문한 상품",productService.getOrderProduct(conditions)
                )
        );
    }






    @RequestMapping(value = "/get/order-list", method = RequestMethod.POST)
    public ResponseEntity<?> getOderList(@RequestBody OrderList orderList, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        orderList.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "주문서",productService.getOrderList(orderList)
                )
        );
    }







    @RequestMapping(value = "/get/my-address", method = RequestMethod.POST)
    public ResponseEntity<?> getMyAddressList(HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);

        return ResponseEntity.ok(
                new Result("200", "배송지 관리",productService.getMyAddressList(memId)
                )
        );
    }


    @RequestMapping(value = "/reg/my-address", method = RequestMethod.POST)
    public ResponseEntity<?> regMyAddress(HttpServletRequest request,@RequestBody MyAddressConditions conditions) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        productService.regMyAddress(conditions);

        return ResponseEntity.ok(
                new Result("200", "배송지 저장"
                )
        );
    }


    @RequestMapping(value = "/mod/my-address", method = RequestMethod.POST)
    public ResponseEntity<?> movMyAddress(HttpServletRequest request,@RequestBody MyAddressConditions conditions) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        productService.modMyAddress(conditions);

        return ResponseEntity.ok(
                new Result("200", "배송지 수정"
                )
        );
    }


    @RequestMapping(value = "/rmv/my-address", method = RequestMethod.POST)
    public ResponseEntity<?> rmvMyAddress(HttpServletRequest request,@RequestBody MyAddressConditions conditions) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        productService.rmvMyAddress(conditions);

        return ResponseEntity.ok(
                new Result("200", "배송지 삭제"
                )
        );
    }

    @RequestMapping(value = "/mod/address-main", method = RequestMethod.POST)
    public ResponseEntity<?> modAddressMain(HttpServletRequest request,@RequestBody MyAddressConditions conditions) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        productService.modAddressMain(conditions);

        return ResponseEntity.ok(
                new Result("200", "대표배송지 지정"
                )
        );
    }

    @RequestMapping(value = "/mod/delivery-completed", method = RequestMethod.POST)
    public ResponseEntity<?> modDeliveryCompleted(HttpServletRequest request,@RequestBody OrderConditions conditions) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        productService.modDeliveryCompleted(conditions);

        return ResponseEntity.ok(
                new Result("200", "배송상태 변경"
                )
        );
    }





    @RequestMapping(value = "/get/reviewed-items", method = RequestMethod.POST)
    public ResponseEntity<?> getReviewedItems(HttpServletRequest request, @RequestBody ReviewItem ri) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        ri.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "리뷰 작성된 상품",
                        productService.getReviewedItem(ri)
                )
        );
    }


    @RequestMapping(value = "/get/reviewable-items", method = RequestMethod.POST)
    public ResponseEntity<?> getReviewableItems(HttpServletRequest request, @RequestBody ReviewItem ri) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        ri.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "리뷰 작성 안 된 상품",
                        productService.getReviewableItem(ri)
                )
        );
    }



    @RequestMapping(value = "/rmv/cartIem", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCartItem(@RequestBody ProductCode pc) {


        productService.deleteCartItem(pc);
        return ResponseEntity.ok(
                new Result("200", "장바구니 아이템 삭제"

                )
        );
    }





   //끝
}

 /*
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getProduct() {

        //return ResponseEntity.noContent().build();
        return ResponseEntity.ok(
                productService.getBestSaleProductList());


     public List<ProductModel> getUser() {
        return productService.getBestSaleProductList();
    }


@RequestMapping( method = RequestMethod.GET)
    public List<ProductModel> getBestSaleProduct() {
        return productService.getBestSaleProductList();
    }

    //return ResponseEntity.noContent().build();

    }*/



