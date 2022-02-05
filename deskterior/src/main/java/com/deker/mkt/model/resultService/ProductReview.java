package com.deker.mkt.model.resultService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProductReview {
    private String mktReviewId;
    private String memId;
    private String productId;
    private String productImg;
    private int myStar;
    private String reviewString;
    private String proReviewImg;
    private Date reviewDate;
    private String productName;
    private String nickname;
    private List<String> productOption;
    private int pageNumber;
    private String profileImg;
    private int start;
    private int end;
}

