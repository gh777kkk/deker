package com.deker.mkt.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyShopping {
    private int deposit;        //입금대기
    private int payment;        //결제완료
    private int preparation;    //배송준비
    private int delivery;       //배송중
    private int complete;       //배송완료
    private int finish;         //구매확정
    private List<MyShoppingItem> orderList;
}
