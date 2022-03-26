package com.deker.post.controller;


import com.deker.cmm.model.Result;
import com.deker.jwt.JwtProvider;
import com.deker.mkt.model.DeliveryStatus;
import com.deker.post.model.*;
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
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        return ResponseEntity.ok(new Result("200","마이페이지 내가올린글",postService.getMemberInfo(conditions)));
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



    @RequestMapping(value = "mb/post/mod/post-detail", method = RequestMethod.POST)
    public ResponseEntity<Result> modPost( HttpServletRequest request, @RequestParam(value = "img", required = false) MultipartFile img,
                                           @RequestPart("community") MyPost mp, @RequestPart("product") List<CommunityProducts> cp) throws IOException {

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        mp.setMemId(memId);
        mp.setCommunityProducts(cp);
        postService.modPost(mp, img);

        return ResponseEntity.ok(new Result("200","게시글 수정"
        ));
    }




    @RequestMapping(value = "/mb/post/get", method = RequestMethod.POST)
    public ResponseEntity<Result> getPost(HttpServletRequest request){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        return ResponseEntity.ok(new Result("200","커뮤니티 메인",
                postService.getPostMain(memId)

        ));
        
    }



    @RequestMapping(value = "/mb/post/get/more", method = RequestMethod.POST)
    public ResponseEntity<Result> getMorePost(HttpServletRequest request,@RequestBody PostConditions conditions){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        return ResponseEntity.ok(new Result("200","커뮤니티 메인 더보기",
                postService.getMorePostMain(conditions)

        ));

    }

    @RequestMapping(value = "/nmb/post/get/more", method = RequestMethod.POST)
    public ResponseEntity<Result> getNbMorePost(HttpServletRequest request, @RequestBody PostConditions conditions){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        conditions.setMemId(memId);
        return ResponseEntity.ok(new Result("200","비회원 커뮤니티 메인 더보기",
                postService.getMorePostMain(conditions)

        ));

    }




    @RequestMapping(value = "/nmb/post/get", method = RequestMethod.POST)
    public ResponseEntity<Result> ngetPost(HttpServletRequest request){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        return ResponseEntity.ok(new Result("200","커뮤니티 메인",
                postService.getPostMain(memId)

        ));

    }



    @RequestMapping(value = "/nmb/post/comments", method = RequestMethod.POST)
    public ResponseEntity<Result> getPostComments(@RequestBody PostCommentConditions conditions){

        //String memId = jwtProvider.getMemIdFromJwtToken(request);
        return ResponseEntity.ok(new Result("200","커뮤니티 댓글",
                postService.getPostComments(conditions)

        ));

    }



    @RequestMapping(value = "/mb/post/reg/comments", method = RequestMethod.POST)
    public ResponseEntity<Result> regPostComments(HttpServletRequest request, @RequestBody PostComment pc){

        String memId = jwtProvider.getMemIdFromJwtToken(request);
        pc.setMemId(memId);
        postService.regPostComments(pc);
        return ResponseEntity.ok(new Result("200","커뮤니티 댓글"

        ));

    }




    @RequestMapping(value = "/nmb/post/get/post-detail", method = RequestMethod.POST)
    public ResponseEntity<Result> getPostDetail(@RequestBody PostDetail pd){

        //String memId = jwtProvider.getMemIdFromJwtToken(request);
        return ResponseEntity.ok(new Result("200","포스트 디테일",
                postService.getPostDetail(pd)

        ));

    }

    @RequestMapping(value = "/mb/post/rmv/post", method = RequestMethod.POST)
    public ResponseEntity<Result> rmvPost(HttpServletRequest request,@RequestBody PostConditions conditions) throws Exception{
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        postService.rmvPost(conditions);
        return ResponseEntity.ok(new Result("200","게시글 삭제 성공"));
    }



    @RequestMapping(value = "/mb/post/reg/post-like", method = RequestMethod.POST)
    public ResponseEntity<Result> regLikePost(HttpServletRequest request,@RequestBody Post conditions) {
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        postService.likePost(conditions);
        return ResponseEntity.ok(new Result("200","게시글 좋아요"));
    }


    @RequestMapping(value = "/mb/post/rmv/post-like", method = RequestMethod.POST)
    public ResponseEntity<Result> rmvDislikePost(HttpServletRequest request,@RequestBody Post conditions) {
        conditions.setMemId(jwtProvider.getMemIdFromJwtToken(request));
        postService.dislikePost(conditions);
        return ResponseEntity.ok(new Result("200","게시글 좋아요 취소"));
    }










}
