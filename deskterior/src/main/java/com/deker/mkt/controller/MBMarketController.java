package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.mkt.model.ProductBuy;
import com.deker.mkt.model.ProductCart;
import com.deker.mkt.model.ProductCode;
import com.deker.mkt.model.RecentProduct;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mb/mkt")
public class MBMarketController {

    public final ProductService productService;

    @RequestMapping( value = "/get", method = RequestMethod.POST)
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok(
                new Result("200", "스토어 메인",
                        productService.getBestSaleProductList())
        );
    }



    @RequestMapping( value = "/get/category",  method = RequestMethod.POST)
    public ResponseEntity<Result> getCategory(@RequestBody ProductCode pc) {

        return ResponseEntity.ok(
                new Result("200", "카테고리 목록",
                        productService.getCategoryList(pc.getCategoryId()))

        );
    }

    @RequestMapping(value = "/get/product-detail", method = RequestMethod.POST)
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pc) {

        productService.insertRecentProduct(pc);

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        Stream.concat(
                                Stream.concat(productService.getProductDetail(pc.getProductId()).stream(),
                                        productService.getProductDetailExplain(pc.getProductId()).stream()),
                                Stream.concat(productService.getRecommendedProduct(pc.getCategoryId()).stream(),
                                        productService.getProductReview(pc.getProductId()).stream())
                        ).collect(Collectors.toList()))
        );
    }



    @RequestMapping(value = "/reg/add-cart", method = RequestMethod.POST)
    public ResponseEntity<?> regCart(@RequestBody ProductCart pc) {

        productService.insertProductCart(pc);
        return ResponseEntity.ok(
                new Result("200", "장바구니 등록"
                        )
        );
    }

    @RequestMapping(value = "/get/buy-now", method = RequestMethod.POST)
    public ResponseEntity<?> getBuyList (@RequestBody ProductBuy pb) {


        return ResponseEntity.ok(
                new Result("200", "결제 페이지",
                        productService.getProductBuyList(pb)
                )
        );
    }


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