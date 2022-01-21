package com.deker.acct.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Acct {
    private String memId;
    private String jwtToken;
    private String extTokenTime;
    private String password;
    private String profileImg;
    private String jobCode;
    private List<String> tag;
    private String id;
    private String nickname;
    private String contents;
}
