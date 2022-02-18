package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostProperties {
    private String communityId;
    private String communityTitle;
    private String userId;
    private String memId;
    private String userNick;
    private String userProfileImg;
    private int likeCount;
    private int commentCount;
    private String followingCheck;
    private String communityImage;
}
