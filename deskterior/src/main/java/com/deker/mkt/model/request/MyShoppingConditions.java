package com.deker.mkt.model.request;

import com.deker.cmm.model.PagingConditions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyShoppingConditions extends PagingConditions {
    private String memId;
    private String period;
    private String deliveryState;
}
