package com.deker.mkt.model.response;

import com.deker.mkt.model.TrackingData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductTracking {
    private String productId;
    private String productOptionId;
    private String productBrand;
    private String productImg;
    private String option1;
    private String option1Data;
    private String option1Nm;
    private String option1DataNm;
    private String option2;
    private String option2Data;
    private String option2Nm;
    private String option2DataNm;
    private String deliveryCode;
    private String deliveryName;
    private String addNmae;
    private String addZip;
    private String address;
    private String waybill;
    private String productExplain;
    private List<String> optionList;
    private List<TrackingData> trackingList;
}
