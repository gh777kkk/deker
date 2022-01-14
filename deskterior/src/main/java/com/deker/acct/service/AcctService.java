package com.deker.acct.service;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import com.deker.exception.MemberNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AcctService {
    Acct regMember(AcctConditions conditions) throws Exception;
    Acct getMemId(AcctConditions conditions) throws Exception;
    Acct memberIdEmailSend(String to)throws Exception;
    Acct memberMailCheck(AcctConditions conditions)throws Exception;
    List<?> setImgTest(MultipartFile img, AcctConditions conditions)throws Exception;
    List<?> getImgTest(AcctConditions conditions, HttpServletRequest request)throws Exception;
}
