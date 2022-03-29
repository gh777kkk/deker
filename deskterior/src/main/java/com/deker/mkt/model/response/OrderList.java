package com.deker.mkt.model.response;

import com.deker.mkt.model.ProductOption;
import com.deker.mkt.model.resultService.MarketAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderList {
    private String memId;
    private String orderId;
    private MarketAddress marketAddress;
    private ProductOption productOption;
    private int totalPrice;
    private int totalDeliveryPay;
    private List<String> cartList;

}
