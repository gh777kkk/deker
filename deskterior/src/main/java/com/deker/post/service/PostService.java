package com.deker.post.service;

import com.deker.cmm.model.PageInfo;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.post.model.MyPost;
import com.deker.post.model.Post;

import com.deker.post.model.PostMain;
import com.deker.post.model.PostConditions;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface PostService {
    PageInfo<Post> getMemberInfo(HttpServletRequest request, PostConditions conditions) throws Exception;

    void regPost(MyPost mp, MultipartFile img) throws IOException;

    PostMain getPostMain(String memId);
}
