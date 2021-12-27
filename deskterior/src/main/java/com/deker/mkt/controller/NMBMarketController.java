package com.deker.mkt.controller;

import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping( value = "/deco",  method = RequestMethod.GET)
    public ResponseEntity<?> getDecoProduct() {
        return ResponseEntity.ok(
                Stream.concat(productService.getDecoProductList().stream(),
                        productService.getNewDecoProductList().stream())
                        .collect(Collectors.toList())
        );
    }


    @RequestMapping( value = "/furniture",  method = RequestMethod.GET)
    public ResponseEntity<?> getFurnitureProduct() {
        return ResponseEntity.ok(
                Stream.concat(productService.getFurnitureProductList().stream(),
                        productService.getNewFurnitureProductList().stream())
                        .collect(Collectors.toList())
        );
    }


    @RequestMapping( value = "/home-appliances",  method = RequestMethod.GET)
    public ResponseEntity<?> getHomeAppliancesProduct() {
        return ResponseEntity.ok(
                Stream.concat(productService.getHomeAppliancesProductList().stream(),
                        productService.getNewHomeAppliancesProductList().stream())
                        .collect(Collectors.toList())
        );
    }


    @RequestMapping( value = "/lamp",  method = RequestMethod.GET)
    public ResponseEntity<?> getLampProduct() {
        return ResponseEntity.ok(
                Stream.concat(productService.getLampProductList().stream(),
                        productService.getNewLampProductList().stream())
                        .collect(Collectors.toList())
        );
    }


    @RequestMapping( value = "/stationery",  method = RequestMethod.GET)
    public ResponseEntity<?> getStationeryProduct() {
        return ResponseEntity.ok(
                Stream.concat(productService.getStationeryProductList().stream(),
                        productService.getNewStationeryProductList().stream())
                        .collect(Collectors.toList())
        );
    }
}
