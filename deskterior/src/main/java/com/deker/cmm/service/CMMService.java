package com.deker.cmm.service;

import com.deker.cmm.model.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CMMService {
    List<CMM> getCode(CMMConditions conditions);
    List<?> setImg(MultipartFile img)throws Exception;
    void startSSE(SseEmitter emitter, HttpServletRequest request);
    CMM sseTest();

    Menu getMenu(HttpServletRequest request);

    void follow(Follow follow);
    void unFollow(Follow follow);

    PageInfo<Follow> getFollowing(Follow follow);

    PageInfo<Follow> getFollower(Follow follow);

    void rmvFollowMe(Follow follow)throws Exception;

    List<Alarm> getAlarm(String memId);
    void modAlarmRead(String alarmId);
}
