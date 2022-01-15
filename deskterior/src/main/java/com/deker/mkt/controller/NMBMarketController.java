package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.mkt.model.request.ProductCode;
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



}
