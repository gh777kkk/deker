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


    public List<ProductModel> getDecoProductList(){

        return productMapper.getDecoProductList();
    }

    public List<ProductModel> getNewDecoProductList(){

        return productMapper.getNewDecoProductList();
    }


    public List<ProductModel> getFurnitureProductList(){

        return productMapper.getFurnitureProductList();
    }

    public List<ProductModel> getNewFurnitureProductList(){

        return productMapper.getNewFurnitureProductList();
    }

    public List<ProductModel> getHomeAppliancesProductList(){

        return productMapper.getHomeAppliancesProductList();
    }

    public List<ProductModel> getNewHomeAppliancesProductList(){

        return productMapper.getNewHomeAppliancesProductList();
    }

    public List<ProductModel> getLampProductList(){

        return productMapper.getLampProductList();
    }

    public List<ProductModel> getNewLampProductList(){

        return productMapper.getNewLampProductList();
    }

    public List<ProductModel> getStationeryProductList(){

        return productMapper.getStationeryProductList();
    }

    public List<ProductModel> getNewStationeryProductList(){

        return productMapper.getNewStationeryProductList();
    }
}
