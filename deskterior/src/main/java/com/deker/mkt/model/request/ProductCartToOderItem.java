package com.deker.mkt.model.request;

import com.deker.mkt.model.resultService.ProductDetailOption;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductCartToOderItem {

    private String myOderId;
    private String orderItemId;
    private String memId;
    private String cartId;
    private String mktProductId;
    private String productName;
    private int productPrice;
    private int productSelectedQuantity;
    private String productImg;
    private ProductDetailOption productDetailOption;
    private int totalPrice;
    private String productOptionId;



}
