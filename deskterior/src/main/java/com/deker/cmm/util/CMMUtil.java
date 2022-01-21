package com.deker.cmm.util;

import com.deker.cmm.mapper.CMMMapper;
import com.deker.cmm.model.Img;
import com.deker.cmm.model.ImgConditions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CMMUtil {
    private final CMMMapper cmmMapper;

    @Value("${img.file.path}")
    private String imgPath;

    @Value("${config.img.url}")
    private String imgUrl;

    @Transactional
    public String nextId(String tableName){
        cmmMapper.updateIds(tableName);
        int nextId = cmmMapper.selectIds(tableName);

        tableName = tableName + "_";
        int length = 20-tableName.length();
        String result = tableName + String.format("%0"+length+"d", nextId);

        return result;
    }

    public String setImg(MultipartFile img,String memId) throws IOException {
        ImgConditions imgConditions = new ImgConditions();
        String filename = img.getOriginalFilename();
        String extention = filename.substring(filename.lastIndexOf('.')+1);
        File file = new File(String.format("%s%s.%s", imgPath, UUID.randomUUID().toString(),extention));
        img.transferTo(file);

        imgConditions.setImgId(nextId("imgId"));
        imgConditions.setStreNm(file.getName());
        imgConditions.setImgNm(filename);
        imgConditions.setImgExtention(extention);
        imgConditions.setImgPath(file.getAbsolutePath());
        imgConditions.setFileSize(file.length());
        imgConditions.setMemId(memId);
        cmmMapper.insertImg(imgConditions);
        return imgConditions.getImgId();
    }

    public String getImg(String imgId) {
        Img img;
        img = cmmMapper.selectImg(imgId);
//        if (img == null) throw new ImgNotFoundException();
        if (img == null) return null;
        return imgUrl + img.getStreNm();
    }
}
