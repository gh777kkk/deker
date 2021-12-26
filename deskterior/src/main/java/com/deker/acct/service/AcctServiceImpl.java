package com.deker.acct.service;

import com.deker.acct.mapper.AcctMapper;
import com.deker.acct.model.AcctConditions;
import com.deker.cmm.util.IDSUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
@Transactional
public class AcctServiceImpl implements AcctService {
    private final AcctMapper acctMapper;
    private final IDSUtil IDSUtil;

    @Override
    public void regMember(AcctConditions conditions){
        conditions.setMemId(IDSUtil.nextId("memId"));
        conditions.setProfileImg("pro_5678901234567890");
        if(conditions.getPlatformCode().equals("P01")){
            acctMapper.insertMember(conditions);
            acctMapper.insertDekerMember(conditions);
        }else {
            acctMapper.insertMember(conditions);
            acctMapper.insertSocialMember(conditions);
        }
    }
}
