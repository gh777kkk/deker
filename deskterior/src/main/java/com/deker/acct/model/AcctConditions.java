package com.deker.acct.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AcctConditions {
    private String memId;
    private String id;
    private String profileImg;
    private String nickname;
    private String jobCode;
    private String agreeYn;
    private String password;
    private String platformCode;
    private String socialId;
    private String checkString;
    private String contents;
    private String memTagId;
    private String authorityCode;
    private List<String> tag;
}
