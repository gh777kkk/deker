package com.deker.mkt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mkt")
public class MarketController {

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<?> getUser() {

        return ResponseEntity.noContent().build();
    }
}
