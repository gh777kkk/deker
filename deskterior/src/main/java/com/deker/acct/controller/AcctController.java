package com.deker.acct.controller;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import com.deker.cmm.model.ResponseData;
import com.deker.jwt.JwtProvider;
import com.deker.security.CustomUserDetailsService;
import com.deker.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class AcctController {

    private final AcctService acctService;

    private final PasswordEncoder passwordEncoder;

//    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AcctController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/mb/cmm/get/job-code", method = RequestMethod.GET)
    public String getJobCode() {
        return "Hello World!";
    }

    // TODO 중복회원가입, 비밀번호 암호화, 프로필 사진, 테그, 익셉션 세분화 처리 해야함
    @RequestMapping(value = "/nmb/test", method = RequestMethod.GET)
    public ResponseData test(AcctConditions conditions) {
        ResponseData result = new ResponseData();
        conditions.setPlatformCode("P01");
        conditions.setId("wkdrjswkd@test.com");
        conditions.setPassword(passwordEncoder.encode("a12345678"));
        conditions.setNickname("아무거나왈왈");
        conditions.setJobCode("J03");
        conditions.setSocialId("12345678");
        conditions.setAgreeYn("Y");
        try{
            acctService.regMember(conditions);
            result.setResponseCode("200");
            result.setMessage("회원가입 성공");
        }catch (Exception e) {
            result.setResponseCode("400");
            result.setMessage("회원가입 실패");
        }
        return result;
    }

    @RequestMapping(value = "/nmb/acct/reg/member", method = RequestMethod.POST)
    public ResponseData regMember(@RequestBody AcctConditions conditions) {
        ResponseData result = new ResponseData();
        if(conditions.getPlatformCode().equals("P01")) conditions.setPassword(passwordEncoder.encode(conditions.getPassword()));
        int state = acctService.regMember(conditions);
        if (state == 1){
            result.setResponseCode("200");
            result.setMessage("회원가입 성공");
        }else if (state == 2 ){
            result.setResponseCode("400");
            result.setMessage("이미 가입한 회원");
        }else {
            result.setResponseCode("400");
            result.setMessage("원인 불명 회원가입 실패");
        }

        return result;
    }

    @RequestMapping(value = "/nmb/acct/get/member", method = RequestMethod.POST)
    public String login(@RequestBody AcctConditions conditions, SecurityUser user) {
        Acct acct = acctService.getMemId(conditions);
        UserDetails authentication =customUserDetailsService.loadUserByUsername(acct.getMemId());
        String jwt = jwtProvider.generateJwtToken(authentication);
        try{
            if (acct.getPlatformCode().equals("P01")){
                if(acct.getPassword().equals(passwordEncoder.encode(conditions.getPassword()))){
                    logger.info("로그인 성공");
                }else new IllegalArgumentException("비밀번호가 다릅니다.");
            }else {
                if(acct.getPlatformCode().equals(conditions.getPlatformCode())){
                    logger.info("로그인 성공");
                }else new IllegalArgumentException("플렛폼 정보가 다릅니다.");
            }
//            return jwtProvider.generateJwtToken();
        }catch (Exception e){
            logger.error("로그인 실패: {}", e);
        }
        return jwt;
    }

    @RequestMapping(value = "/nmb/acct/email/test", method = RequestMethod.POST)
    public void emailTest() throws Exception {
        String confirm = acctService.sendSimpleMessage("wkdrjswkd5@naver.com");
    }
}
