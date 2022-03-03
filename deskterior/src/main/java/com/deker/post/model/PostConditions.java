package com.deker.post.model;

import com.deker.cmm.model.PagingConditions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostConditions extends PagingConditions {
    private String memId;
    private String postId;
}
