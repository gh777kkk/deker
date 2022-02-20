package com.deker.mkt.model.resultService;

import com.deker.cmm.model.PagingConditions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProductReview extends PagingConditions {
    private String mktReviewId;
    private String memId;
    private String productId;
    private String productImg;
    private int myStar;
    private String reviewString;
    private String proReviewImg;
    private String reviewDate;
    private String productName;
    private String nickname;
    private List<String> productOption;
    private int pageNumber;
    private String profileImg;
    private int start;
    private int end;
    private String orderId;
    private String optionId;
    private String option2DataName;
    private String option1DataName;

}

