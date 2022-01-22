package com.deker.post.service;

import com.deker.post.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {
    Post getMemberInfo(HttpServletRequest request) throws Exception;
}
