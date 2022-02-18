package com.deker.post.controller;


import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.DeliveryStatus;
import com.deker.post.model.CommunityProducts;
import com.deker.post.model.MyPost;
import com.deker.post.model.PostConditions;
import com.deker.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtProvider jwtProvider;

    @RequestMapping(value = "/mb/post/get/my-post-list", method = RequestMethod.POST)
    public ResponseEntity<Result> getMyPostList(HttpServletRequest request,@RequestBody PostConditions conditions) throws Exception{
        return ResponseEntity.ok(new Result("200","마이페이지 내가올린글",postService.getMemberInfo(request,conditions)));
    }


    @RequestMapping(value = "/mb/post/reg/write-post", method = RequestMethod.POST)
    public ResponseEntity<Result> regPost( HttpServletRequest request, @RequestParam(value = "img", required = false) MultipartFile img,
                                          @RequestPart("community") MyPost mp, @RequestPart("product") List<CommunityProducts> cp) throws IOException {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        mp.setMemId(memId);
        mp.setCommunityProducts(cp);
        postService.regPost(mp, img);


        return ResponseEntity.ok(new Result("200","게시글 작성"
                ));
    }



    @RequestMapping(value = "/mb/post", method = RequestMethod.POST)
    public ResponseEntity<Result> regPost(HttpServletRequest request){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        return ResponseEntity.ok(new Result("200","커뮤니티 메인",
                postService.getPostMain(memId)

        ));
        
    }

}
