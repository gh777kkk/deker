package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPost {
    private String communityId;
    private String communityTitle;
    private String communityContent;
    private String jobCode;
    private String materialCode;
    private String moodCode;
    private List<String> communityTags;
    private CommunityProducts communityProducts;






}
