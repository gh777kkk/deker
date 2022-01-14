package com.deker.acct.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Acct {
    private String memId;
    private String jwtToken;
    private Date extTokenTime;
    private String password;
}
