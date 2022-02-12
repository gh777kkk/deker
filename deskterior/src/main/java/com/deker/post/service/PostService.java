package com.deker.post.service;

import com.deker.mkt.model.resultService.ProductReview;
import com.deker.post.model.MyPost;
import com.deker.post.model.Post;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {
    Post getMemberInfo(HttpServletRequest request) throws Exception;

    void regPost(MyPost mp, MultipartFile img);
}
