package com.deker.acct.controller;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.acct.service.AcctService;
import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.security.CustomUserDetailsService;
import com.deker.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AcctController {

    private final AcctService acctService;

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(AcctController.class);

    @RequestMapping(value = "/nmb/acct/reg/member", method = RequestMethod.POST)
    public ResponseEntity<Result> regMember(@RequestBody AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","회원가입 성공",acctService.regMember(conditions)));
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

    @RequestMapping(value = "/nmb/acct/member/mail-send", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailSend(@RequestBody AcctConditions conditions) throws Exception {
        acctService.memberIdEmailSend(conditions.getId());
        return ResponseEntity.ok(new Result("200","메일 발송 완료"));
    }

    @RequestMapping(value = "/nmb/acct/get/member/mail/check", method = RequestMethod.POST)
    public ResponseEntity<Result> memberMailCheck(@RequestBody AcctConditions conditions) throws Exception {
        acctService.memberMailCheck(conditions);
        return ResponseEntity.ok(new Result("200","정상"));
    }

    @RequestMapping(value = "/nmb/reg/img-test", method = RequestMethod.POST)
    public ResponseEntity<Result> regImgTest(@RequestParam("img") MultipartFile img,AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.setImgTest(img,conditions)));
    }

    @RequestMapping(value = "/nmb/get/img-test", method = RequestMethod.POST)
    public ResponseEntity<Result> getImgTest(AcctConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",acctService.getImgTest(conditions)));
    }
}
