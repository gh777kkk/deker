package com.deker.post.model;

import com.deker.cmm.model.PagingConditions;
import com.deker.cmm.model.TenPagingConditions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentConditions extends TenPagingConditions {
    private String communityId;
}
