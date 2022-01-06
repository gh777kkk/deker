package com.deker.acct.service;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;

import java.util.List;

public interface AcctService {
    int regMember(AcctConditions conditions);
    Acct getMemId(AcctConditions conditions);
    int memberIdEmailSend(String to)throws Exception;
}
