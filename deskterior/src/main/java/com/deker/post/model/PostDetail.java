package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDetail {
    private List<CommunityProducts> communityPostSelectedProduct;
    private MyPost communityPost;
    private String communityPostId;
    private String memId;

}
