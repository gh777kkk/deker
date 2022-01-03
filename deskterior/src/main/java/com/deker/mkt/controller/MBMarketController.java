package com.deker.mkt.controller;

import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mb/mkt")
public class MBMarketController {

    public final ProductService productService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok(
                productService.getBestSaleProductList());
    }

    @RequestMapping( value = "/category/{code}",  method = RequestMethod.GET)
    public ResponseEntity<?> getDecoProduct(@PathVariable("code") String code) {

        return ResponseEntity.ok(
                Stream.concat(productService.getBestCategoryProductList(code).stream(),
                        productService.getNewCategoryProductList(code).stream())
                        .collect(Collectors.toList())
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