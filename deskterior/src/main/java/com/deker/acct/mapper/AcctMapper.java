package com.deker.acct.mapper;

import com.deker.acct.model.Acct;
import com.deker.acct.model.AcctConditions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AcctMapper {
    void insertMember(AcctConditions conditions);
    void insertDekerMember(AcctConditions conditions);
    void insertSocialMember(AcctConditions conditions);
    Acct selectMemCheck(AcctConditions conditions);
    void updateMailCheck(AcctConditions conditions);
    List<String> selectNicknameCheck(AcctConditions conditions);
    Acct selectMailCheckString(AcctConditions conditions);
}
