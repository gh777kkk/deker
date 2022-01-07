package com.deker.mkt.service;

import com.deker.cmm.util.IDSUtil;
import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
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


    public List<ProductDetailModel> getProductDetail(String productId){

        return productMapper.getProductDetail(productId);
    }
    public List<ProductDetailExplain> getProductDetailExplain(String productId){

        return productMapper.getProductDetailExplain(productId);
    }
    public List<RecommendedProduct> getRecommendedProduct(String productId){

        return productMapper.getRecommendedProduct(productId);
    }
    public List<ProductReview> getProductReview(String productId){

        return productMapper.getProductReview(productId);
    }
}
