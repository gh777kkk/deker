package com.deker.cmm.service;

import com.deker.cmm.mapper.CMMMapper;
import com.deker.cmm.model.*;
import com.deker.cmm.util.CMMUtil;
import com.deker.exception.MemberNotFoundException;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.ProductModel;
import com.deker.post.model.CommunityProducts;
import com.deker.post.model.PostComment;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
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

    @Value("${menu.main.img}")
    private String mainMenuImg;

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
        CMM cmm = new CMM();
        cmm.setValue("sse 연결");
        try {
            emitter.send(cmm,MediaType.APPLICATION_JSON);
        }catch (Exception e){
            emitter.complete();
        }
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

    @Override
    public List<Alarm> getAlarm(String memId){
        return cmmMapper.selectAlarm(memId);
    }

    @Override
    public void modAlarmRead(String alarmId){
        cmmMapper.updateAlarmRead(alarmId);
    }


    //메뉴

    public Menu getMenu(HttpServletRequest request){




        String authorityCode;
        String memId = jwtProvider.getMemIdFromJwtToken(request);
        if(memId==null){
            authorityCode = "ROLE_ANONYMOUS";
        }
        else{
            authorityCode = cmmMapper.getUserRole(memId);
        }

        Menu m = new Menu();
        List<Menu> menu = new ArrayList<>();
        SubMenu subMenu = new SubMenu();
        List<Menu> community = new ArrayList<>();
        List<Menu> market = new ArrayList<>();

        List<Menu> menus = cmmMapper.getMenu(authorityCode);

        for (Menu me : menus) {

            if(me.getMenuParent() == 0){
                menu.add(me);
            }
            else{
                if(me.getMenuParent() == 200000000){
                    community.add(me);
                }
                else if(me.getMenuParent() == 300000000){
                    market.add(me);
                }
            }
        }
        subMenu.setCommunity(community);
        subMenu.setMarket(market);

        m.setMenu(menu);
        m.setSubMenu(subMenu);
        m.setMenuImgUrl(mainMenuImg);

        return m;

    }


    public void follow(Follow follow){
        cmmMapper.follow(follow);
    }

    public void unFollow(Follow follow){
        cmmMapper.unFollow(follow);
    }


    public PageInfo<Follow> getFollowing(Follow follow){

        int nonpagedCount = cmmMapper.getFollowingCount(follow.getMemId());
        PageInfo<Follow> pageInfo = new PageInfo<>(follow,nonpagedCount);


        pageInfo.setTotalCount(nonpagedCount);
        List<Follow> follows = cmmMapper.getFollowing(follow);
        for (Follow f : follows){
            f.setProfile_img(CMMUtil.getImg(f.getProfile_img()));
        }
        pageInfo.setList(follows);


        return pageInfo;
    }


    public PageInfo<Follow> getFollower(Follow follow){

        int nonpagedCount = cmmMapper.getFollowerCount(follow.getMemId());
        PageInfo<Follow> pageInfo = new PageInfo<>(follow,nonpagedCount);


        pageInfo.setTotalCount(nonpagedCount);
        List<Follow> follows = cmmMapper.getFollower(follow);
        for (Follow f : follows){
            f.setProfile_img(CMMUtil.getImg(f.getProfile_img()));
        }
        pageInfo.setList(follows);


        return pageInfo;
    }

    public void rmvFollowMe(Follow follow)throws Exception{
        if (follow.getUserId() == null || follow.getUserId().equals("")) throw new MemberNotFoundException();
        cmmMapper.deleteFollowMe(follow);
    }

}
