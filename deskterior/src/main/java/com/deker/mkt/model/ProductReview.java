package com.deker.mkt.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ProductReview {
    private int myStar;
    private String reviewString;
    private Date reviewDate;
    private String memId;
    private String productName;
}
