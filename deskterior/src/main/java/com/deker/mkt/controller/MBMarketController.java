package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.mkt.model.request.ProductCart;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.request.ProductOrder;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/mb/mkt")
public class MBMarketController {

    public final ProductService productService;
    private final JwtProvider jwtProvider;

    @RequestMapping(method = RequestMethod.POST)
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
    public ResponseEntity<?> getProduct(@RequestBody ProductCode pc, HttpServletRequest request) {

        //String memid = jwtProvider
        //pc.setMemId(memid);
        productService.insertRecentProduct(pc);

        return ResponseEntity.ok(
                new Result("200", "상품 디테일",
                        productService.getProductDetails(pc))
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
    public ResponseEntity<?> getBuyList(@RequestBody ProductBuy pb) {


        return ResponseEntity.ok(
                new Result("200", "결제 페이지",
                        productService.getProductBuyList(pb)
                )
        );
    }

    @RequestMapping(value = "/get/tracking", method = RequestMethod.POST)
    public ResponseEntity<?> getTracking(@RequestBody ProductOrder conditions) throws Exception {


        return ResponseEntity.ok(
                new Result("200", "배송 조회",
                        productService.getProductTracking(conditions)
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