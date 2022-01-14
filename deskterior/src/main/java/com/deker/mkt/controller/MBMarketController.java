package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.mkt.model.ProductCart;
import com.deker.mkt.model.ProductCode;
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
                productService.getBestSaleProductList());
    }



    @RequestMapping( value = "/get/category",  method = RequestMethod.POST)
    public ResponseEntity<Result> getCategory(@RequestBody ProductCode pc) {

        return ResponseEntity.ok(
                new Result("200", "카테고리 목록",
                        Stream.concat(productService.getBestCategoryProductList(pc.getCategoryId()).stream(),
                                productService.getNewCategoryProductList(pc.getCategoryId()).stream()
                        ).collect(Collectors.toList()))
        );
    }

    @RequestMapping(value = "/get/product-detail", method = RequestMethod.POST)
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pm) {

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        Stream.concat(
                                Stream.concat(productService.getProductDetail(pm.getProductId()).stream(),
                                        productService.getProductDetailExplain(pm.getProductId()).stream()),
                                Stream.concat(productService.getRecommendedProduct(pm.getCategoryId()).stream(),
                                        productService.getProductReview(pm.getProductId()).stream())
                        ).collect(Collectors.toList()))
        );
    }


    @RequestMapping(value = "/reg/add-cart", method = RequestMethod.POST)
    public ResponseEntity<?> regCart(@RequestBody ProductCart pc) {

        productService.regProductCart(pc);
        return ResponseEntity.ok(
                new Result("200", "장바구니 등록"
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