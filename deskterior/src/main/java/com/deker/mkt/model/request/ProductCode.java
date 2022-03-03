package com.deker.mkt.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCode {
    private String productId;
    private String categoryId;
    private String memId;
    private List<String> cartIdArr;
    private String orderId;
}
