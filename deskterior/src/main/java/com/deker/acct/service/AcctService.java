package com.deker.acct.service;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.exception.MemberNotFoundException;

import java.util.List;

public interface AcctService {
    List<?> regMember(AcctConditions conditions) throws MemberNotFoundException, Exception;
    Acct getMemId(AcctConditions conditions);
    void memberIdEmailSend(String to)throws Exception;
    public void memberMailCheck(AcctConditions conditions)throws Exception;
}
