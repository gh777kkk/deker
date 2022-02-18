package com.deker.post.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostComment {

    private Date commentUpdateYmdt;
    private String writeNickname;
    private String writerProfileImgUrl;
    private String content;
}
