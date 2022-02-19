package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostCommentList {

    private String communityId;
    private int totalCommentCount;
    private List<PostComment> commentList;
}
