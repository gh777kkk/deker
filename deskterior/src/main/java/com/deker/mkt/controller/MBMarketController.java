package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.request.Payment;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mb/mkt")
public class MBMarketController {

    public final ProductService productService;
    private final JwtProvider jwtProvider;
    private final IamportService iamportService;


    //reg, get, mod, del

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인",
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




    @RequestMapping(value = "/get/verify", method = RequestMethod.POST)
    public ResponseEntity<?> getVerifyPayment(@RequestBody Payment pm, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(
                new Result("200", "결제 페이지",
                        iamportService.getBuyerInfor(pm.getImp_uid())
                )
        );
    }


    @RequestMapping(value = "/get/payment-complete", method = RequestMethod.POST)
    public ResponseEntity<?> getPaymentComplete(@RequestBody Payment pm, HttpServletRequest request) {
        return ResponseEntity.ok(
                new Result("200", "결제 완료"
                //상품 값 수정

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