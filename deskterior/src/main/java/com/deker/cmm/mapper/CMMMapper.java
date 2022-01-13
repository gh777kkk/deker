package com.deker.cmm.mapper;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.model.Img;
import com.deker.cmm.model.ImgConditions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CMMMapper {
    List<CMM> selectCode(CMMConditions conditions);
    void updateIds(String tableName);
    Integer selectIds(String tableName);
    void insertImg(ImgConditions conditions);
    Img selectImg(String imgId);
}
