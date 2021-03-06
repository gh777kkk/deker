package com.deker.cmm.controller;

import com.deker.cmm.model.Result;
import com.deker.cmm.service.CMMService;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductKeyword;
import com.deker.mkt.model.request.ProductCode;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.mkt.service.IamportService;
import com.deker.mkt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MenuController {

    public final ProductService productService;
    private final JwtProvider jwtProvider;
    private final CMMService cmmService;

    //reg, get, mod, del

    @RequestMapping(value = "/nmb/cmm/get/nav-menu", method = RequestMethod.POST)
    public ResponseEntity<?> getNmbMenu(HttpServletRequest request) {

        return ResponseEntity.ok(
                new Result("200", "비회원 메뉴",
                        cmmService.getMenu(request))
        );
    }

    @RequestMapping(value = "/mb/cmm/get/nav-menu", method = RequestMethod.POST)
    public ResponseEntity<?> getMbMenu(HttpServletRequest request) {

        return ResponseEntity.ok(
                new Result("200", "회원 메뉴",
                        cmmService.getMenu(request))
        );
    }







}
