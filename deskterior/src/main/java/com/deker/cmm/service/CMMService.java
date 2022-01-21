package com.deker.cmm.service;

import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CMMService {
    List<CMM> getCode(CMMConditions conditions);
    List<?> setImg(MultipartFile img)throws Exception;
    void startSSE(SseEmitter emitter, HttpServletRequest request);
    CMM sseTest();
}
