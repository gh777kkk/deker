package com.deker.cmm.controller;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.model.Follow;
import com.deker.cmm.model.Result;
import com.deker.cmm.service.CMMService;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.request.ProductBuy;
import com.deker.post.model.PostConditions;
import com.deker.security.CustomUserDetailsService;
import com.deker.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CMMController {
    private final CMMService cmmService;
    private final JwtProvider jwtProvider;


    @RequestMapping(value = "/nmb/cmm/get/code", method = RequestMethod.POST)
    public ResponseEntity<?> getCode(@RequestBody CMMConditions conditions) {
        return ResponseEntity.ok(
                new Result("200", "코드 조회 성공",
                        cmmService.getCode(conditions)
                )
        );
    }

    @RequestMapping(value = "/nmb/file/test", method = RequestMethod.POST)
    public List<CMM> fileTest(CMMConditions conditions, @RequestParam("profileImg")MultipartFile multipartFile) {
        conditions.setCodeId("JOB");
        return cmmService.getCode(conditions);
    }


    @RequestMapping(value = "/nmb/reg/img", method = RequestMethod.POST)
    public ResponseEntity<Result> regImgTest(@RequestParam("img") MultipartFile img,@RequestPart(value="conditions", required=false) CMMConditions conditions) throws Exception {
        return ResponseEntity.ok(new Result("200","정상",cmmService.setImg(img)));
    }

    @RequestMapping(value = "/mb/sse-start", method = RequestMethod.POST, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseStart(HttpServletRequest request) throws Exception {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        cmmService.startSSE(emitter,request);
        return emitter;
    }

    @RequestMapping(value = "/nmb/sse-test", method = RequestMethod.POST)
    public ResponseEntity<Result> sseTest() throws Exception {
        cmmService.sseTest();
        return ResponseEntity.ok(new Result("200","정상"));
    }


    @RequestMapping(value = "/mb/cmm/reg/follow", method = RequestMethod.POST)
    public ResponseEntity<Result> regFollow(HttpServletRequest request,@RequestBody Follow follow) {
        follow.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        cmmService.follow(follow);
        return ResponseEntity.ok(new Result("200","팔로우"));
    }



    @RequestMapping(value = "/mb/cmm/rmv/follow", method = RequestMethod.POST)
    public ResponseEntity<Result> rmvFollow(HttpServletRequest request,@RequestBody Follow follow) {
        follow.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        cmmService.unFollow(follow);
        return ResponseEntity.ok(new Result("200","언팔로우"));
    }



    @RequestMapping(value = "/mb/cmm/get/follow", method = RequestMethod.POST)
    public ResponseEntity<Result> getFollow(HttpServletRequest request,@RequestBody Follow follow) {

        follow.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        return ResponseEntity.ok(
                new Result("200","팔로우 목록",
                cmmService.getFollowing(follow)
                ));
    }

}
