package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.request.*;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;

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
        productService.insertRecentProduct(pc);

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        productService.getProductDetails(pc))
        );
    }


    @RequestMapping(value = "/get/recent-pro", method = RequestMethod.POST)
    public ResponseEntity<?> getRecentProduct( HttpServletRequest request) {

//        String memId = jwtProvider.getMemIdFromJwtToken(request);
//        pc.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "장바구니 목록"
                )
        );
    }


    @RequestMapping(value = "/reg/add-cart", method = RequestMethod.POST)
    public ResponseEntity<?> regCart(@RequestBody ProductCart pc, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pc.setMemId(memId);
        productService.insertProductCart(pc);
        return ResponseEntity.ok(
                new Result("200", "장바구니 등록"
                )
        );
    }

    @RequestMapping(value = "/get/cart", method = RequestMethod.POST)
    public ResponseEntity<?> getCart( HttpServletRequest request) {

//        String memId = jwtProvider.getMemIdFromJwtToken(request);
//        pc.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "장바구니 목록"
                )
        );
    }


    @RequestMapping(value = "/get/buy-now", method = RequestMethod.POST)
    public ResponseEntity<?> getBuyList(@RequestBody ProductBuy pb, HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pb.setMemId(memId);
        return ResponseEntity.ok(
                new Result("200", "결제 페이지",
                        productService.getProductBuyList(pb)
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
                new Result("200", "결제 페이지",
                        iamportService.getBuyerInfor(pm.getImp_uid())
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
    public ResponseEntity<?> regProductReview( @RequestParam("myImg") MultipartFile myImg, ProductReview pr, HttpServletRequest request) throws Exception {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pr.setMemId(memId);
        productService.regReview(pr,myImg);

        return ResponseEntity.ok(
                new Result("200", "리뷰 작성"
                )
        );
    }



    @RequestMapping(value = "/mod/review", method = RequestMethod.POST)
    public ResponseEntity<?> modReview(@RequestParam("myImg") MultipartFile myImg,
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



