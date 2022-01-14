package com.deker.cmm.util;

import com.deker.cmm.mapper.CMMMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IDSUtil {
    private final CMMMapper cmmMapper;

    @Transactional
    public String nextId(String tableName){
        cmmMapper.updateIds(tableName);
        int nextId = cmmMapper.selectIds(tableName);

        tableName = tableName + "_";
        int length = 20-tableName.length();
        String result = tableName + String.format("%0"+length+"d", nextId);

        return result;
    }
}