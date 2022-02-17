package com.deker.post.mapper;

import com.deker.post.model.CommunityProducts;
import com.deker.post.model.MyPost;
import com.deker.post.model.Post;
import com.deker.post.model.PostConditions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectMyPostList(PostConditions conditions);
    Post selectMemberProfileImg(PostConditions conditions);

    void insertPost(MyPost myPost);
    void insertPostDetail(MyPost myPost);
    void insertPostTag(MyPost myPost);
    void insertPostItem(CommunityProducts cp);
}
