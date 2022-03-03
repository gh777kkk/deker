package com.deker.cmm.controller;

import com.deker.cmm.model.Result;
import com.deker.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler({ MemberNotFoundException.class })
    public ResponseEntity<Result> handleMemberNotFoundException() {
        return ResponseEntity.ok(new Result("400","회원 정보를 찾을 수 없습니다"));
    }
    @ExceptionHandler({ AlreadyMemberException.class })
    public ResponseEntity<Result> handleAlreadyMemberException() {
        return ResponseEntity.ok(new Result("400","이미 가입한 회원입니다"));
    }
    @ExceptionHandler({ AlreadyNicknameException.class })
    public ResponseEntity<Result> handleAlreadyNicknameException() {
        return ResponseEntity.ok(new Result("400","닉네임 중복"));
    }
    @ExceptionHandler({ MailCheckNotFoundException.class })
    public ResponseEntity<Result> handleMailCheckNotFoundException() {
        return ResponseEntity.ok(new Result("400","메일을 발송한 적 없는 아이디"));
    }
    @ExceptionHandler({ ImgNotFoundException.class })
    public ResponseEntity<Result> handleImgNotFoundException() {
        return ResponseEntity.ok(new Result("400","이미지를 찾을 수 없습니다"));
    }
    @ExceptionHandler({ LoginPasswordException.class })
    public ResponseEntity<Result> handleLoginPasswordException() {
        return ResponseEntity.ok(new Result("400","패스워드가 다릅니다"));
    }
    @ExceptionHandler({ LoginSocialIdException.class })
    public ResponseEntity<Result> handleNoPaymentException() {
        return ResponseEntity.ok(new Result("401","소셜 아이디가 다릅니다"));
    }
    @ExceptionHandler({ TrackingKeyException.class })
    public ResponseEntity<Result> handleTrackingKeyException() {
        return ResponseEntity.ok(new Result("401","Tracking 키값 오류"));
    }
    @ExceptionHandler({ TrackingException.class })
    public ResponseEntity<Result> handleTrackingException(TrackingException e) {
        return ResponseEntity.ok(new Result("400",e.getMsg()));
    }

    @ExceptionHandler({ PaymentVerificationException.class })
    public ResponseEntity<Result> handleLoginSocialIdException() {
        return ResponseEntity.ok(new Result("401","결제 검증을 실패했습니다."));
    }

    @ExceptionHandler({ PostIdMemIdDifferentException.class })
    public ResponseEntity<Result> handlePostIdMemIdDifferentException() {
        return ResponseEntity.ok(new Result("400","게시글 등록자가 아닙니다"));
    }


    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Result> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.ok(new Result("410",e.toString()));
    }
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Result> handleException(Exception e) {
        return ResponseEntity.ok(new Result("420",e.toString()));
    }
}
