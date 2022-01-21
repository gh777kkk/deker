package com.deker.mkt.service;

import com.deker.exception.PaymentVerificationException;
import com.deker.mkt.model.request.CancleBuy;
import com.deker.mkt.model.request.Iamport;
import com.deker.mkt.model.request.Payment;
import com.deker.mkt.model.response.BuyerInfor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class IamportService {


    private String imp_key = "4575938639252862";
    private String imp_secret = "57c3d96bafb050ee142eff600cdb053d6901e5144dfad44e75b66f4ff9075963cd160263c3bf430a";
    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();
    JSONObject body = new JSONObject();



    public Object getBuyerInfor(String imp_uid) throws Exception{
        Iamport iamport = getToken();
        try{
            if(iamport==null){
                throw new Exception();
            }
            headers.clear();
            headers.add("Authorization",(String) iamport.getResponse().get("access_token"));
            HttpEntity<JSONObject>entity = new HttpEntity<JSONObject>(headers);

            BuyerInfor buyerInfor = restTemplate.postForObject("https://api.iamport.kt/payments/"+imp_uid+"", entity, BuyerInfor.class);
            System.out.println(buyerInfor+"fullinfor");
            return buyerInfor.getResponse().get("amount");

        } catch (Exception e){
            e.printStackTrace();
            throw new PaymentVerificationException();
        }

    }

    public void cancleBuy(String imp_uid){

        try{
            Iamport iamport = getToken();
            if(iamport==null){
                throw new Exception();
            }
            headers.clear();
            headers.add("Authorization",(String) iamport.getResponse().get("access_token"));
            body.clear();
            body.put("imp_uid", imp_uid);


            HttpEntity<JSONObject>entity = new HttpEntity<JSONObject>(body, headers);
            CancleBuy cancle = restTemplate.postForObject("https://api.iamport.kr/payments/cancle",entity,CancleBuy.class);

            System.out.println(cancle+"full cancle");

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        //return true;
    }





    public Iamport getToken(){


        headers.setContentType(MediaType.APPLICATION_JSON);

        body.put("imp_key", imp_key);
        body.put("imp_secret", imp_secret);

        HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject("https://api.iamport.kt/users/getToken", entity, Iamport.class);

//        System.out.println(token + "fulltoken");
//        System.out.println(token.getStatusCode() + "tgetsoken");
//        System.out.println(token.getStatusCodeValue() + "getvaltoken");
//        System.out.println(token.getBody() + "bodytoken");
//        System.out.println(token.getBody().get("response") + "bodytoken");
    }




}
