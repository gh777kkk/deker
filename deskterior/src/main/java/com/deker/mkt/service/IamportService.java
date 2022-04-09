package com.deker.mkt.service;

import com.deker.exception.PaymentVerificationException;
import com.deker.mkt.model.request.CancleBuy;
import com.deker.mkt.model.request.Iamport;
import com.deker.mkt.model.request.Payment;
import com.deker.mkt.model.response.BuyerInfor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class IamportService {


    public final ProductService productService;
    private String imp_key = "7009134484888523";
    private String imp_secret = "4fae91423c5b987c764a3a486b343741c3fbe23ccacdf0f694df2c353c6ca0edfe2b62c11ea6b7fb";
    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();
    JSONObject body = new JSONObject();



    public boolean getBuyerInfor(Payment pm) throws Exception{
        String imp_uid = pm.getImp_uid();
        Iamport iamport = getToken();
        System.out.println(iamport.getResponse().get("access_token"));

        try{
            if(iamport==null){
                throw new Exception();
            }
            headers.clear();
            headers.add("Authorization",(String) iamport.getResponse().get("access_token"));
            HttpEntity<JSONObject>entity = new HttpEntity<JSONObject>(headers);

            BuyerInfor buyerInfor = restTemplate.postForObject("https://api.iamport.kr/payments/"+imp_uid+"", entity, BuyerInfor.class);
            if(pm.getPaid_amount() ==  Integer.parseInt(buyerInfor.getResponse().get("amount").toString())){

                productService.modProduct(pm);

                return true;
            }
           return false;

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
        return restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, Iamport.class);


    }




}
