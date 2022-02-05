package com.deker.acct.controller;

import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import com.deker.cmm.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AcctController {

    private final AcctService acctService;

    @RequestMapping(value = "/nmb/acct/reg/member", method = RequestMethod.POST)
    public ResponseEntity<Result> regMember(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","회원가입 - 로그인",acctService.regMember(conditions)));
    }

    @RequestMapping(value = "/nmb/acct/get/member", method = RequestMethod.POST)
    public ResponseEntity<Result> login(@RequestBody AcctConditions conditions) throws Exception{
        return ResponseEntity.ok(new Result("200","로그인",acctService.getMemId(conditions)));
    }

    @RequestMapping(value = "/mb/acct/get/member-info", method = RequestMethod.POST)
    public ResponseEntity<Result> getMemberInfo(HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(new Result("200","회원정보",acctService.getMemberInfo(request)));
    }

    @RequestMapping(value = "/mb/acct/mod/member-info", method = RequestMethod.POST)
    public ResponseEntity<Result> modMemberInfo(@RequestParam("profileImg") MultipartFile profileImg,AcctConditions conditions,HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(new Result("200","회원정보 변경",acctService.modMemberInfo(profileImg,conditions,request)));
    }

    @RequestMapping(value = "/nmb/acct/member/mail-send", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailSend(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","메일 발송",acctService.memberIdEmailSend(conditions.getId())));
    }

    @RequestMapping(value = "/nmb/acct/get/member/mail/check", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailCheck(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.memberMailCheck(conditions)));
    }
}
