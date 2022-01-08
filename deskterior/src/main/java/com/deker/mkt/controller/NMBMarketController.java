package com.deker.mkt.controller;

import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/nmb/mkt")
public class NMBMarketController {

    public final ProductService productService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok(
                productService.getBestSaleProductList());
    }


    @RequestMapping( value = "/get/category/{code}",  method = RequestMethod.GET)
    public ResponseEntity<?> getDecoProduct(@PathVariable("code") String code) {

        return ResponseEntity.ok(
                Stream.concat(productService.getBestCategoryProductList(code).stream(),
                        productService.getNewCategoryProductList(code).stream())
                        .collect(Collectors.toList())
        );


    }

}
