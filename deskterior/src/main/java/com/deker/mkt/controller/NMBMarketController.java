package com.deker.mkt.controller;

import com.deker.cmm.model.PagingConditions;
import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductKeyword;
import com.deker.mkt.model.request.ProductCategoryConditions;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import com.deker.mkt.service.ProductSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/nmb/mkt")
public class NMBMarketController {

    public final ProductService productService;
    private final JwtProvider jwtProvider;
    private final IamportService iamportService;
    private final ProductSession productSession;

    //reg, get, mod, del

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인",
                        productService.getBestSaleProductList())
        );
    }

    @RequestMapping(value = "/get/product/more", method = RequestMethod.POST)
    public ResponseEntity<?> getProductMore(PagingConditions pc) {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인 더보기",
                        productService.getMoreBestSaleProductList(pc))
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
    public ResponseEntity<?> getCategoryProductMore(@RequestBody ProductCategoryConditions conditions) {

        return ResponseEntity.ok(
                new Result("200", "스토어 카테고리 상품 더보기",
                        productService.getMoreCategoryList(conditions))
        );
    }



    @RequestMapping(value = "/get/product-detail", method = RequestMethod.POST)
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pc, HttpServletRequest request) {


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






    @RequestMapping(value = "/get/verify", method = RequestMethod.POST)
    public void getVerifyPayment() {

//         String imp_key = "4575938639252862";
//         String imp_secret = "57c3d96bafb050ee142eff600cdb053d6901e5144dfad44e75b66f4ff9075963cd160263c3bf430a";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        JSONObject body = new JSONObject();
//        body.put("imp_key", imp_key);
//        body.put("imp_secret", imp_secret);
//
//        HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
//        ResponseEntity<JSONObject> token = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", entity, JSONObject.class);
//
//        System.out.println(token + "fulltoken");
//        System.out.println(token.getStatusCode() + "tgetsoken");
//        System.out.println(token.getStatusCodeValue() + "getvaltoken");
//        System.out.println(token.getBody() + "bodytoken");
//        System.out.println(token.getBody().get("response") + "bodytoken");



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

    @RequestMapping(value = "/get/testt", method = RequestMethod.POST)
    public void getttProduct(@RequestBody ProductCode pc) {

//        DeliveryUpdate du = new DeliveryUpdate();
//        String timeString = "1999-06-03 10:45:00";
//        String id = "odrId_99999999999999";
//        du.setId(id);
//        du.setTimeString(timeString);
//        productMapper.updateConfirmDate(du);

        //sd.deliveryStatus();

    }



    @RequestMapping(value = "/reg/recent-product", method = RequestMethod.POST)
    public ResponseEntity<?> regRecentProduct(@RequestBody ProductCode pc, HttpServletRequest request) {

        productService.nmbRegRecentProduct(pc.getProductId(),request);

        return ResponseEntity.ok(
                new Result("200", "최근 본 상품 등록"
                )
        );
    }



    @RequestMapping(value = "/get/recent-product", method = RequestMethod.POST)
    public ResponseEntity<?> getRecentProduct(HttpServletRequest request) {

         //pc.setProductId(productSession.getProductId());

        return ResponseEntity.ok(
                new Result("200", "최근 본 상품",
                        productService.nmbGetRecentProduct(request)
                )
        );
    }

    @RequestMapping(value = "/get/ctest", method = RequestMethod.POST)
    public Object getctest(HttpServletResponse response) {


        ResponseCookie cookie = ResponseCookie.from("sameSiteCookie", "sameSiteCookieValue")
                .domain("211.232.166.228:6015")
                .sameSite("None")
                .secure(true)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return "성공";
    }



    @RequestMapping(value = "/get/cctest", method = RequestMethod.POST)
    public Object getcctest(@CookieValue(name = "memId", required = false) String memId) {


        return memId;

    }





}
