package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.DeliveryUpdate;
import com.deker.mkt.model.ProductKeyword;
import com.deker.mkt.model.request.Payment;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import com.deker.mkt.service.Scheduler;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/nmb/mkt")
public class NMBMarketController {

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

        //productService.insertRecentProduct(pc);

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        productService.getProductDetails(pc))
        );
    }


    @RequestMapping(value = "/get/recommend-product", method = RequestMethod.POST)
    public ResponseEntity<?> getRecoProduct(@RequestBody ProductCode pc) {


        return ResponseEntity.ok(
                new Result("200", "추천 상품",
                        productService.getRecoProduct(pc))
        );
    }


    @RequestMapping(value = "/get/review", method = RequestMethod.POST)
    public ResponseEntity<?> getRecoProduct(@RequestBody ProductReview pr) throws ParseException {


        return ResponseEntity.ok(
                new Result("200", "상품 리뷰",
                       productService.getProductReview(pr))
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



    @RequestMapping(value = "/get/test", method = RequestMethod.POST)
    public void gettProduct(@RequestBody ProductCode pc) {

//        DeliveryUpdate du = new DeliveryUpdate();
//        String timeString = "1999-06-03 10:45:00";
//        String id = "odrId_99999999999999";
//        du.setId(id);
//        du.setTimeString(timeString);
//        productMapper.updateConfirmDate(du);

        //sd.deliveryStatus();

    }




    @RequestMapping(value = "/get/verify", method = RequestMethod.POST)
    public void getVerifyPayment() {

         String imp_key = "4575938639252862";
         String imp_secret = "57c3d96bafb050ee142eff600cdb053d6901e5144dfad44e75b66f4ff9075963cd160263c3bf430a";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("imp_key", imp_key);
        body.put("imp_secret", imp_secret);

        HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> token = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", entity, JSONObject.class);

        System.out.println(token + "fulltoken");
        System.out.println(token.getStatusCode() + "tgetsoken");
        System.out.println(token.getStatusCodeValue() + "getvaltoken");
        System.out.println(token.getBody() + "bodytoken");
        System.out.println(token.getBody().get("response") + "bodytoken");



    }


    @RequestMapping(value = "/get/reg-product", method = RequestMethod.POST)
    public ResponseEntity<?> getRegProductList(@RequestBody ProductKeyword pk) {

        return ResponseEntity.ok(
                new Result("200", "상품 등록 리스트",
                        productService.getRegProduct(pk))
        );
    }


    @RequestMapping(value = "/get/my", method = RequestMethod.POST)
    public ResponseEntity<?>  getTest() {

        productService.getTest();

        return ResponseEntity.ok(
                new Result("200", "상품 등록 리스트")
        );



    }



}
