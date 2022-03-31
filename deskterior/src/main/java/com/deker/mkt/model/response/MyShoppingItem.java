package com.deker.mkt.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class MyShoppingItem {
    private String orderNumber;
    private List<MyShoppingList> orderList;
}
