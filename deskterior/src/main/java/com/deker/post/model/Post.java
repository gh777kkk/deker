package com.deker.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private String postId;
    private String postImg;
    private String profileImg;
    private String memId;
    private List<Post> postList;
}
