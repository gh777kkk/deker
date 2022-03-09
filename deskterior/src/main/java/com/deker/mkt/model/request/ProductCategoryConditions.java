package com.deker.mkt.model.request;

import com.deker.cmm.model.PagingConditions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryConditions extends PagingConditions {
    private String categoryId;
}
