package com.deker.post.mapper;

import com.deker.post.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectMyPostList(PostConditions conditions);
    Integer selectMyPostListCount(PostConditions conditions);
    Post selectMemberProfileImg(PostConditions conditions);

    void insertPost(MyPost myPost);
    void insertPostDetail(MyPost myPost);
    void insertPostTag(MyPost myPost);
    void insertPostItem(CommunityProducts cp);

    PostProperties getPostDetail(String communityId);
    List<PostProperties> getPostLike();
    int getPostCommentCount(String communityId);
    boolean getPostFollow(PostProperties pp);



    int getMorePostCount();
    List<PostProperties> getMorePostLike(PostConditions pc);
    int getPostMyFollowCount(PostConditions pc);
    List<PostProperties> getMorePostMyFollow(PostConditions pc);
    int getMorePostCustomCount(PostConditions pc);
    List<PostProperties> getMorePostCustom(PostConditions pc);


    List<PostComment> getPostComment(PostCommentConditions conditions);
    void insertPostComment(PostComment pc);


    MyPost getSelectPostDetail(String communityId);
    List<MyPost> getPostTag (String postDetailId);
    List<CommunityProducts> getPostProduct (String postDetailId);

    List<PostProperties> getPostMyFollow(String memId);
    List<PostProperties> getPostCustom(String memId);


    List<PostProperties> getPostNew(List<PostProperties> pp);

    Post selectPostMemId(PostConditions conditions);
    void deletePostList(PostConditions conditions);
    void deletePostDetail(PostConditions conditions);
}
