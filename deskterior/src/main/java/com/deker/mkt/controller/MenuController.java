package com.deker.mkt.controller;

import com.deker.cmm.model.Result;
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
    private final IamportService iamportService;

    //reg, get, mod, del

    @RequestMapping(value = "/nmb", method = RequestMethod.POST)
    public ResponseEntity<?> getNmbMenu() {

        return ResponseEntity.ok(
                new Result("200", "비회원 메뉴",
                        productService.getNmbMenu())
        );
    }



    @RequestMapping(value = "/mb", method = RequestMethod.POST)
    public ResponseEntity<?> getMbMenu(HttpServletRequest request) {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        //pc.setMemId(memId);

        return ResponseEntity.ok(
                new Result("200", "회원 메뉴",
                        productService.getMbMenu())
        );

    }



}
