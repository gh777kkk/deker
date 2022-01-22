package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.mkt.model.request.Payment;
import com.deker.mkt.model.request.ProductCode;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/nmb/mkt")
public class NMBMarketController {

    public final ProductService productService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
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
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pc) {

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        productService.getProductDetails(pc))
        );
    }

    @RequestMapping(value = "/get/test", method = RequestMethod.POST)
    public void gettProduct(@RequestBody ProductCode pc) {


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



}
