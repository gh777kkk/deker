package com.deker.post.controller;


import com.deker.cmm.model.Result;
import com.deker.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/mb/post/get/my-post-list", method = RequestMethod.POST)
    public ResponseEntity<Result> getMyPostList(HttpServletRequest request) throws Exception{
        return ResponseEntity.ok(new Result("200","마이페이지 내가올린글",postService.getMemberInfo(request)));
    }
}
