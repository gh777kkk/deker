package com.deker.cmm.controller;

import com.deker.cmm.model.Result;
import com.deker.exception.AlreadyMemberException;
import com.deker.exception.AlreadyNicknameException;
import com.deker.exception.MailCheckNotFoundException;
import com.deker.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler({ MemberNotFoundException.class })
    public ResponseEntity<Result> handleMemberNotFoundException() {
        return ResponseEntity.ok(new Result("400","없는 회원 입니다"));
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
        return ResponseEntity.ok(new Result("400","실패"));
    }
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Result> handleRuntimeException() {
        return ResponseEntity.ok(new Result("420","에러 code : 1"));
    }
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Result> handleException() {
        return ResponseEntity.ok(new Result("430","에러 code : 2"));
    }
}
