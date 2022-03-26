package com.deker.post.service;

import com.deker.cmm.model.Follow;
import com.deker.cmm.model.PageInfo;
import com.deker.mkt.model.resultService.ProductReview;
import com.deker.post.model.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface PostService {
    PageInfo<Post> getMemberInfo(PostConditions conditions) throws Exception;

    void regPost(MyPost mp, MultipartFile img) throws IOException;

    void modPost(MyPost mp, MultipartFile img) throws IOException;


    PostMain getPostMain(String memId);
    PostMain getNPostMain();

    PageInfo<PostProperties> getMorePostMain(PostConditions pc);
    PageInfo<PostProperties> getNMorePostMain(PostConditions pc);


    PageInfo<PostComment> getPostComments(PostCommentConditions conditions);

    void regPostComments(PostComment pc);

    PostDetail getPostDetail(PostDetail pd);
    PostDetail getMPostDetail(PostDetail pd);



    void rmvPost(PostConditions conditions) throws Exception;

   void likePost(Post post);
   void dislikePost(Post post);


}
