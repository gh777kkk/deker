package com.deker.mkt.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class RecentProduct {

    private String mktRecentProductId;
    private String productId;
    private String memId;
    private Date nowDate;

}
