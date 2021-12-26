package com.deker.cmm.service;

import com.deker.cmm.mapper.CMMMapper;
import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.util.IDSUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CMMServiceImpl implements CMMService {
    private final CMMMapper cmmMapper;
    private final IDSUtil IDSUtil;

    @Override
    public List<CMM> getCode(CMMConditions conditions){
        IDSUtil.nextId("memId");
        return cmmMapper.selectCode(conditions);
    }
}
