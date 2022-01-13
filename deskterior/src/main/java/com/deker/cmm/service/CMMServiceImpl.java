package com.deker.cmm.service;

import com.deker.cmm.mapper.CMMMapper;
import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CMMServiceImpl implements CMMService {
    private final CMMMapper cmmMapper;

    @Override
    public List<CMM> getCode(CMMConditions conditions){
        return cmmMapper.selectCode(conditions);
    }
}
