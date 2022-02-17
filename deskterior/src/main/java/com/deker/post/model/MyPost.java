package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPost {
    private String memId;
    private String postImg;
    private String communityId;
    private String communityTitle;
    private String communityContent;
    private String jobCode;
    private String materialCode;
    private String moodCode;
    private String postDetailId;
    private String postTagId;
    private String tag;
    private List<String> communityTags;
    private List<CommunityProducts> communityProducts;







}
