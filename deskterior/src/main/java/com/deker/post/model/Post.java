package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Post {
    private String postId;
    private String postImg;
    private String profileImg;
    private List<Post> postList;
}
