package com.deker.cmm.service;

import com.deker.cmm.mapper.CMMMapper;
import com.deker.cmm.model.CMM;
import com.deker.cmm.model.CMMConditions;
import com.deker.cmm.util.CMMUtil;
import com.deker.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CMMServiceImpl implements CMMService {

    private final CMMMapper cmmMapper;

    private final JwtProvider jwtProvider;

    private final com.deker.cmm.util.CMMUtil CMMUtil;

    private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    @Override
    public List<CMM> getCode(CMMConditions conditions){
        return cmmMapper.selectCode(conditions);
    }

    @Override
    @Transactional
    public List<?> setImg(MultipartFile img)throws Exception{
        CMMUtil.setImg(img,"SYSTEM");
        return null;
    }

    @Override
    public void startSSE(SseEmitter emitter, HttpServletRequest request){
        String memId = jwtProvider.getMemIdFromJwtToken(request);
        CLIENTS.put(memId, emitter);
        emitter.onTimeout(() -> CLIENTS.remove(memId));
        emitter.onCompletion(() -> CLIENTS.remove(memId));
        emitter.onError((e) -> CLIENTS.remove(memId));
    }

    @Override
    public CMM sseTest(){
        CMM cmm = new CMM();
        cmm.setValue("sse 전송");
        CLIENTS.forEach((id,emitter)->{
            try{
                emitter.send(cmm, MediaType.APPLICATION_JSON);
            }catch (Exception e){
                emitter.complete();
            }
        });
        return null;
    }
}
