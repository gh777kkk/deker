package com.deker.acct.mapper;

import com.deker.acct.model.AcctConditions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcctMapper {
    void insertMember(AcctConditions conditions);
    void insertDekerMember(AcctConditions conditions);
    void insertSocialMember(AcctConditions conditions);
}
