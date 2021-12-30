package com.deker.mkt.service;

import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public List<ProductModel> getBestSaleProductList(){

        return productMapper.getBestSaleProductList();
    }


    public List<ProductModel> getBestCategoryProductList(String code){

        return productMapper.getBestCategoryProductList(code);
    }

    public List<ProductModel> getNewCategoryProductList(String code){

        return productMapper.getNewCategoryProductList(code);
    }

}
