package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follow extends PagingConditions {
    private String userId;
    private String memId;
    private String keyword;
    private String profile_img;
    private String nickNm;
}
