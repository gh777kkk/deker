package com.deker.acct.service;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;

import java.util.List;

public interface AcctService {
    void regMember(AcctConditions conditions);
    Acct getMemId(AcctConditions conditions);
}
