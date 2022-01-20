package com.deker.acct.controller;

import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import com.deker.cmm.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AcctController {

    private final AcctService acctService;

    @RequestMapping(value = "/nmb/acct/reg/member", method = RequestMethod.POST)
    public ResponseEntity<Result> regMember(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","회원가입 성공",acctService.regMember(conditions)));
    }

    @RequestMapping(value = "/nmb/acct/get/member", method = RequestMethod.POST)
    public ResponseEntity<Result> login(@RequestBody AcctConditions conditions) throws Exception{
        return ResponseEntity.ok(new Result("200","로그인 성공",acctService.getMemId(conditions)));
    }

    @RequestMapping(value = "/nmb/acct/member/mail-send", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailSend(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","메일 발송 완료",acctService.memberIdEmailSend(conditions.getId())));
    }

    @RequestMapping(value = "/nmb/acct/get/member/mail/check", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailCheck(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.memberMailCheck(conditions)));
    }

    @RequestMapping(value = "/nmb/reg/img-test", method = RequestMethod.POST)
    public ResponseEntity<Result> regImgTest(@RequestParam("img") MultipartFile img,AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.setImgTest(img,conditions)));
    }

    @RequestMapping(value = "/nmb/get/img-test", method = RequestMethod.POST)
    public ResponseEntity<Result> getImgTest(AcctConditions conditions, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.getImgTest(conditions,request)));
    }

    @RequestMapping(value = "/nmb/get/trc-test", method = RequestMethod.POST)
    public ResponseEntity<Result> getTrcTest(@RequestBody AcctConditions conditions) throws Exception {
        acctService.getTrcTest(conditions);
        return ResponseEntity.ok(new Result("200","정상"));
    }
}
