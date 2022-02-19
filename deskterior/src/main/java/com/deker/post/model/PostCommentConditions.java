package com.deker.post.model;

import com.deker.cmm.model.PagingConditions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentConditions extends PagingConditions {
    private String communityId;
}
