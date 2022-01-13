package com.deker.cmm.service;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CMMService {
    List<CMM> getCode(CMMConditions conditions);
}
