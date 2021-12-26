package com.deker.acct.controller;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import com.deker.cmm.model.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AcctController {

    private final AcctService acctService;

    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/nmb/cmm/get/job-code", method = RequestMethod.GET)
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
        conditions.setNickName("아무거나왈왈");
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
    public void regMember(@RequestBody AcctConditions conditions) {
    }

    @RequestMapping(value = "/nmb/acct/get/member", method = RequestMethod.POST)
    public void getMember(@RequestBody AcctConditions conditions) {
    }
}
